package com.servicematrix.client;

import com.servicematrix.client.netty.NettyClient;
import com.servicematrix.client.netty.ServerMessageHandler;
import com.servicematrix.msg.*;
import io.netty.channel.ChannelFuture;
import org.apache.log4j.BasicConfigurator;


public class ClientMessageSender{

    private ChannelFuture channelFuture;

    private String key;

    private RequestHeader clientRequestHeader;

    public ClientMessageSender(String host, Integer port, ServerMessageHandler serverMessageHandler) throws Exception {
        BasicConfigurator.configure();
        NettyClient nettyClient = new NettyClient();
        nettyClient.init(host,port,serverMessageHandler);
        this.channelFuture = nettyClient.getChannelFuture();

    }

    public void sendMessage(RequestHeader requestHeader,RequestBody requestBody){
        requestHeader.setMessageType(RequestMessageType.NORMAL);
        clientRequestHeader = requestHeader;
        RequestMessage requestMessage = new RequestMessage(key,clientRequestHeader,requestBody);
        channelFuture.channel().writeAndFlush(requestMessage);
    }

    public void sendMessage(RequestBody requestBody){
        clientRequestHeader.setMessageType(RequestMessageType.NORMAL);
        RequestMessage requestMessage = new RequestMessage(key,clientRequestHeader,requestBody);
        channelFuture.channel().writeAndFlush(requestMessage);
    }


    public void bind(String key,RequestHeader requestHeader,RequestBody requestBody){
        requestHeader.setMessageType(RequestMessageType.BIND);
        this.key = key;
        this.clientRequestHeader = requestHeader;
        RequestMessage bindMessage = new RequestMessage(key,requestHeader,requestBody);
       // bindMessage.setKey("person01");
        channelFuture.channel().writeAndFlush(bindMessage);
    }

    public void unbind(String key,RequestHeader requestHeader,RequestBody requestBody){
        requestHeader.setMessageType(RequestMessageType.UNBIND);
        RequestMessage unbindMessage = new RequestMessage(key,requestHeader,requestBody);
       // unbindMessage.setKey("person01");
        channelFuture.channel().writeAndFlush(unbindMessage);
        try {
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void mapEngine(RequestHeader requestHeader,RequestBody requestBody){
        requestHeader.setMessageType(RequestMessageType.ENGINE);
        RequestMessage engineMessage = new RequestMessage(requestHeader,requestBody);
        channelFuture.channel().writeAndFlush(engineMessage);
    }

    public void sendAccessibleMap(RequestBody requestBody){
        RequestHeader header = new RequestHeader();
        header.setMessageType(RequestMessageType.ACCESSIBLE_MAP);
        RequestMessage accessibleMessage = new RequestMessage(header,requestBody);
        channelFuture.channel().writeAndFlush(accessibleMessage);
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
