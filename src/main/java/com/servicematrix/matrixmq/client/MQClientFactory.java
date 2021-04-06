package com.servicematrix.matrixmq.client;

import com.google.common.base.Preconditions;
import com.servicematrix.matrixmq.netty.RequestDataDecoder;
import com.servicematrix.matrixmq.netty.ResponseDataEncoder;
import com.servicematrix.matrixmq.serialize.KryoCodecUtil;
import com.servicematrix.matrixmq.serialize.KryoPoolFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class MQClientFactory {
    private volatile boolean connected = false;

    private String brokerHost;

    private int brokerPort;

    private static KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());

    private ChannelFuture messageChannel = null;

    private ChannelInboundHandler channelInboundHandler = null;

    private Bootstrap bootstrap = null;

    private EventLoopGroup eventLoopGroup = null;

    public MQClientFactory(String brokerHost, int brokerPort) {
        this.brokerHost = brokerHost;
        this.brokerPort = brokerPort;
    }

    public void init() {
        try {
            eventLoopGroup = new NioEventLoopGroup(1);
            bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new RequestDataDecoder(util));
                            channel.pipeline().addLast(new ResponseDataEncoder(util));
                            channel.pipeline().addLast(channelInboundHandler);
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void connect() {
        Preconditions.checkNotNull(channelInboundHandler, "Message's Handler is Null!");

        try {
            init();
            ChannelFuture channelFuture = bootstrap.connect(this.brokerHost,this.brokerPort).sync();
            //messageChannel = channelFuture.channel();
            channelFuture.addListener((ChannelFutureListener) future -> {
                if(future.isSuccess()){
                    messageChannel = future;
                    System.out.println(messageChannel.channel().localAddress().toString()+"  已连接...");
                }
            });
            connected = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (messageChannel != null) {
            try {
                messageChannel.channel().close().sync();
                eventLoopGroup.shutdownGracefully();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }





    public ChannelFuture getMessageChannel() {
        return messageChannel;
    }

    public void setMessageChannel(ChannelFuture messageChannel) {
        this.messageChannel = messageChannel;
    }

    public ChannelInboundHandler getChannelInboundHandler() {
        return channelInboundHandler;
    }

    public void setChannelInboundHandler(ChannelInboundHandler channelInboundHandler) {
        this.channelInboundHandler = channelInboundHandler;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getBrokerHost() {
        return brokerHost;
    }

    public void setBrokerHost(String brokerHost) {
        this.brokerHost = brokerHost;
    }

    public int getBrokerPort() {
        return brokerPort;
    }

    public void setBrokerPort(int brokerPort) {
        this.brokerPort = brokerPort;
    }
}
