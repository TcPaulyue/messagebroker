package com.servicematrix.client;

import com.servicematrix.client.netty.NettyClient;
import com.servicematrix.client.netty.ServerMessageHandler;
import com.servicematrix.example.ResponseMessageReceiver;
import com.servicematrix.msg.*;
import io.netty.channel.ChannelFuture;
import org.apache.log4j.BasicConfigurator;


public class ClientMessageSender{

    private ChannelFuture channelFuture;

    public ClientMessageSender(String host, Integer port, ServerMessageHandler serverMessageHandler) throws Exception {
        BasicConfigurator.configure();
        NettyClient nettyClient = new NettyClient();
        nettyClient.init(host,port,new ResponseMessageReceiver());
        this.channelFuture = nettyClient.getChannelFuture();

    }

    public void sendMessage(RequestHeader requestHeader,RequestBody requestBody){
        requestHeader.setMessageType(RequestMessageType.NORMAL);
        RequestMessage requestMessage = new RequestMessage(requestHeader,requestBody);
       // requestMessage.setKey("person01");
        channelFuture.channel().writeAndFlush(requestMessage);
    }

    public void bind(RequestHeader requestHeader,RequestBody requestBody){
        requestHeader.setMessageType(RequestMessageType.BIND);
        RequestMessage bindMessage = new RequestMessage(requestHeader,requestBody);
       // bindMessage.setKey("person01");
        channelFuture.channel().writeAndFlush(bindMessage);
    }

    public void unbind(RequestHeader requestHeader,RequestBody requestBody){
        requestHeader.setMessageType(RequestMessageType.UNBIND);
        RequestMessage unbindMessage = new RequestMessage(requestHeader,requestBody);
       // unbindMessage.setKey("person01");
        channelFuture.channel().writeAndFlush(unbindMessage);
        try {
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
//    public static void main(String[] args) throws Exception {
//        ClientMessageSender messageHandler = new ClientMessageSender(new ClientMessageFactory());
//        messageHandler.init("localhost",8080);
//        messageHandler.sendMessage(topics.get(new Random().nextInt(topics.size())),messages.get(new Random().nextInt(messages.size())));
//        messageHandler.sendMessage(topics.get(new Random().nextInt(topics.size())),messages.get(new Random().nextInt(messages.size())));
//        messageHandler.sendMessage(topics.get(new Random().nextInt(topics.size())),messages.get(new Random().nextInt(messages.size())));
//        messageHandler.closeChannel();
//
//    }
}
