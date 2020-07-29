package com.servicematrix;

import com.servicematrix.client.ClientHandler;
import com.servicematrix.client.RequestDataEncoder;
import com.servicematrix.client.ResponseDataDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class MessageConnectFactory {

    private String host = "localhost";

    private Integer port = 8080;

    private Bootstrap bootstrap;

    private ClientHandler clientHandler = new ClientHandler();

    private Channel messageChannel;

    public void init(){
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                    ch.pipeline().addLast(new RequestDataEncoder(),
                            new ResponseDataDecoder(), clientHandler);
                }
            });
            ChannelFuture f = bootstrap.connect(host, port).sync();
            messageChannel = f.channel();
    } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Channel getMessageChannel() {
        return messageChannel;
    }
}
