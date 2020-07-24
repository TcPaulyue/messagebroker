package com.servicematrix;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    String host = "localhost";
    int port = 8080;


    public static void main(String[] args) throws Exception {
        new NettyClient().run();

    }

    public void run() throws Exception{
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                    ch.pipeline().addLast(new RequestDataEncoder(),
                        new ResponseDataDecoder(), new ClientHandler());
                }
            });
        ChannelFuture f = b.connect(host, port).sync();
        f.channel().closeFuture().sync();
    } finally {
        workerGroup.shutdownGracefully();

        }
    }
}