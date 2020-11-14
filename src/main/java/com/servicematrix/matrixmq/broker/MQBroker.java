package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.netty.RequestDataDecoder;
import com.servicematrix.matrixmq.netty.ResponseDataEncoder;
import com.servicematrix.matrixmq.serialize.KryoCodecUtil;
import com.servicematrix.matrixmq.serialize.KryoPoolFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MQBroker {
    public static final String BROKERID = "MATRIXMQ_01";

    private int port;

    private static KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());


    private boolean isRunning = false;

    public MQBroker(int port) {
        this.port = port;
    }

    public void run(int concurrentNum) {
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
                            ch.pipeline().addLast(
                                    new RequestDataDecoder(util),
                                    new ResponseDataEncoder(util),
                                    new MessageBrokerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = null;
            try {
                f = b.bind(port).sync();
                isRunning = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ExecutorService executorService = Executors.newFixedThreadPool(concurrentNum*3);
            for(int i =0;i<concurrentNum;i++){
                executorService.execute(new AckBindController());
                executorService.execute(new RequestMessageController());
                executorService.execute(new RequestMessageCacheController());
            }
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            isRunning = false;
        }
    }

    public static void main(String[] args) {
        MQBroker mqBroker = new MQBroker(8080);
        mqBroker.run(1);

    }

    public boolean isRunning() {
        return isRunning;
    }
}
