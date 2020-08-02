package com.servicematrix.client;

import com.servicematrix.msg.*;
import com.servicematrix.client.netty.NettyClient;
import com.servicematrix.msg.Location;
import com.servicematrix.msg.LoginClientMessage;
import com.servicematrix.msg.LogoutClientMessage;
import com.servicematrix.msg.NormalClientMessage;


public class MessageHandler extends NettyClient {

    public enum MessageType{
        LOGIN,TEXT,LOGOUT
    }

//    protected static List<String> topics = Arrays.asList("machine1", "machine2", "machine3");
//
//    protected static List<String> messages = Arrays.asList("Donald", "Theresa", "Vladimir", "Angela", "Emmanuel", "Shinzō", "Jacinda", "Kim");

    private MessageFactory messageFactory;

    MessageHandler(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }


    void sendMessage(String id, long time, Location location, String message, String destination) throws InterruptedException {
        NormalClientMessage msg = (NormalClientMessage) messageFactory.newMessage(MessageType.TEXT,id,time,location);
        msg.setDestination(destination);
        msg.setMessage(message);
        channelFuture.channel().writeAndFlush(msg);
       // channelFuture.channel().closeFuture().sync();
    }

    void login(String id, long time, Location location){
        LoginClientMessage loginMessage = (LoginClientMessage) messageFactory.newMessage(MessageType.LOGIN,id,time,location);
        loginMessage.setLoginSign("LOGIN");
        channelFuture.channel().writeAndFlush(loginMessage);
    }

    void logout(String id, long time, Location location){
        LogoutClientMessage logoutMessage = (LogoutClientMessage)messageFactory.newMessage(MessageType.LOGOUT,id,time,location);
        logoutMessage.setLogoutSign("LOGOUT");
        channelFuture.channel().writeAndFlush(logoutMessage);

    }

    public void init(String host, Integer port) throws Exception {
        super.init(host,port);
    }


//    public static void main(String[] args) throws Exception {
//        MessageHandler messageHandler = new MessageHandler(new MessageFactory());
//        messageHandler.init("localhost",8080);
//        messageHandler.sendMessage(topics.get(new Random().nextInt(topics.size())),messages.get(new Random().nextInt(messages.size())));
//        messageHandler.sendMessage(topics.get(new Random().nextInt(topics.size())),messages.get(new Random().nextInt(messages.size())));
//        messageHandler.sendMessage(topics.get(new Random().nextInt(topics.size())),messages.get(new Random().nextInt(messages.size())));
//        messageHandler.closeChannel();
//
//    }
}
