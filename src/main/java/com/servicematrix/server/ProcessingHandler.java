package com.servicematrix.server;

import com.servicematrix.msg.*;
import com.servicematrix.scheduling.SchedulingMethod;
import com.servicematrix.scheduling.SchedulingMethodDemo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


import java.util.concurrent.ConcurrentHashMap;


public class ProcessingHandler extends ChannelInboundHandlerAdapter {

    private ServerMessageFactory serverMessageFactory;

    public ProcessingHandler(ServerMessageFactory serverMessageFactory) {
        this.serverMessageFactory = serverMessageFactory;
    }

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static ConcurrentHashMap<String, ChannelInfo> channelInfoMap = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("===========================");
        if (msg instanceof LoginClientMessage) {
            System.out.println("a new client -" + ctx.channel().remoteAddress() + " /log in...");
            LoginClientMessage loginMessage = (LoginClientMessage) msg;
            channelInfoMap.put(loginMessage.getId(), new ChannelInfo(ctx.channel()
                    , ((LoginClientMessage) msg).getTime()
                    , ((LoginClientMessage) msg).getLocation()));

            ReplyMessage replyMessage = (ReplyMessage) serverMessageFactory.newMessage(ServerMessageType.LOGIN_REPLY);
            replyMessage.setTime(System.currentTimeMillis());
            replyMessage.setReplyMessage("login succeed...");
            ctx.channel().writeAndFlush(replyMessage);

        } else if (msg instanceof LogoutClientMessage) {
            System.out.println("a connected client -" + ctx.channel().remoteAddress() + " /log out...");
            LogoutClientMessage logoutMessage = (LogoutClientMessage) msg;
            channelInfoMap.remove(logoutMessage.getId());

            ReplyMessage replyMessage = (ReplyMessage) serverMessageFactory.newMessage(ServerMessageType.LOGOUT_REPLY);
            replyMessage.setTime(System.currentTimeMillis());
            replyMessage.setReplyMessage("log out server...");

            ctx.channel().writeAndFlush(replyMessage);


        } else if (msg instanceof NormalClientMessage) {
            System.out.println("a connected client -" + ctx.channel().remoteAddress() + " /send message");

            ClientMessage clientMessage = (NormalClientMessage) msg;

            channelInfoMap.forEach((key, channelInfo) -> {
                SchedulingMethod schedulingMethod = new SchedulingMethodDemo();
                if (key.equals((clientMessage).getId())) {
                    ReplyMessage replyMessage = (ReplyMessage) serverMessageFactory.newMessage(ServerMessageType.MULTICAST_REPLY);
                    replyMessage.setTime(System.currentTimeMillis());
                    replyMessage.setReplyMessage("message has been sent to messageBroker...");
                    channelInfo.getChannel().writeAndFlush(replyMessage);

                } else {
                    if (schedulingMethod.judge(clientMessage, channelInfo)) {
                        RoutingMessage routingMessage = (RoutingMessage) serverMessageFactory.newMessage(ServerMessageType.MULTICAST);
                        routingMessage.setClientMessage(clientMessage);
                        routingMessage.setTime(System.currentTimeMillis());
                        channelInfo.getChannel().writeAndFlush(routingMessage);
                    }
                }
            });
        } else {
            String message = (String) msg;
            System.out.println(message);
        }
        System.out.println("连接数: " + channelInfoMap.size() + "\n");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx){
        channelGroup.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx){
        channelGroup.remove(ctx.channel());
    }

    public Boolean checkConnection(){
        if(channelInfoMap.size()!=channelGroup.size())
            return false;
        else
            return true;
    }
}