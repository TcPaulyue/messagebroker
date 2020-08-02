package com.servicematrix.client.netty;

import com.servicematrix.serialize.KryoCodecUtil;
import com.servicematrix.serialize.KryoPoolFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public abstract class NettyClient {

    private String host;
    private int port;

    protected ChannelFuture channelFuture;

    private EventLoopGroup workerGroup;


    private static KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());

  //  protected abstract void sendMessage(String message,String  topic) throws InterruptedException;



    public void init(String host,Integer port) throws Exception{

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
                        new ResponseDataDecoder(util), new ClientHandler());
                }
            });
        ChannelFuture f = b.connect(this.host, this.port).sync();
        f.addListener((ChannelFutureListener)future ->{
                if(future.isSuccess()){
                    System.out.println("客户端[" + f.channel().localAddress().toString() + "]已连接...");
                }
        });
        this.channelFuture = f;
    } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeChannel() throws InterruptedException {
        channelFuture.channel().closeFuture().sync();
        workerGroup.shutdownGracefully();
    }


}