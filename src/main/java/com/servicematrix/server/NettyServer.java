package com.servicematrix.server;

import com.servicematrix.msg.ServerMessageFactory;
import com.servicematrix.serialize.KryoCodecUtil;
import com.servicematrix.serialize.KryoPoolFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class NettyServer {

    private static KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());

    public static final String serverId = "MessageBroker_01";

    private static final String schedulingMethodName = "SchedulingMethodDemo";

    private int port;

    private ServerMessageFactory serverMessageFactory;

    private static final Logger logger = Logger.getLogger(NettyServer.class);

    public NettyServer(int port, ServerMessageFactory serverMessageFactory) {
        this.port = port;
        this.serverMessageFactory = serverMessageFactory;
    }

    public NettyServer() {
    }

    public NettyServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new RequestDecoder(util),
                                    new ResponseDataEncoder(util),
                                    new ProcessingHandler(serverMessageFactory, schedulingMethodName));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    public void start(Integer port) throws Exception {
        BasicConfigurator.configure();
        logger.info("netty server start...");
        new NettyServer(port, new ServerMessageFactory()).run();
    }

//    public static void main(String[] args) throws Exception {
//        BasicConfigurator.configure();
//        int workerThreads = Runtime.getRuntime().availableProcessors() * 2;
//        logger.info(workerThreads);
//        int port;
//        logger.info("netty server start...");
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        } else {
//            port = 8082;
//        }
//        new NettyServer(port, new ServerMessageFactory()).run();
//    }
}