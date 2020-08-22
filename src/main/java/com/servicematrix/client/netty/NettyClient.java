package com.servicematrix.client.netty;

import com.servicematrix.serialize.KryoCodecUtil;
import com.servicematrix.serialize.KryoPoolFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;


public class NettyClient {

    private String host;

    private int port;

    private ChannelFuture channelFuture;

    private EventLoopGroup workerGroup;

    private ServerMessageHandler serverMessageHandler;

    private static final Logger logger = Logger.getLogger(NettyClient.class);

    private static KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());



    public void init(String host,Integer port,ServerMessageHandler serverMessageHandler) throws Exception{

        workerGroup = new NioEventLoopGroup(1);
        this.host = host;
        this.port = port;
        try{
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                    ch.pipeline().addLast(new RequestDataEncoder(util),
                        new ResponseDataDecoder(util), serverMessageHandler);
                }
            });
        ChannelFuture f = b.connect(this.host, this.port).sync();
        f.addListener((ChannelFutureListener)future ->{
                if(future.isSuccess()){
                    logger.info("客户端[" + f.channel().localAddress().toString() + "]已连接...");
                }
        });
        this.channelFuture = f;
    } catch (Exception e){
            e.printStackTrace();
        }
    }


    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }


    public void closeChannel() throws InterruptedException {
        channelFuture.channel().closeFuture().sync();
        workerGroup.shutdownGracefully();
    }


}