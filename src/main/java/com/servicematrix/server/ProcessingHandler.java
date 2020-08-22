package com.servicematrix.server;

import com.servicematrix.msg.*;
import com.servicematrix.scheduling.SchedulingMethod;
import com.servicematrix.scheduling.SchedulingMethodDemo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.log4j.Logger;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;


public class ProcessingHandler extends ChannelInboundHandlerAdapter {

    private ServerMessageFactory serverMessageFactory;

    private String schedulingMethodName;

    private static final Logger logger = Logger.getLogger(ProcessingHandler.class);

    public ProcessingHandler(ServerMessageFactory serverMessageFactory, String schedulingMethodName) {
        this.serverMessageFactory = serverMessageFactory;
        this.schedulingMethodName = "com.servicematrix.scheduling." + schedulingMethodName;
    }

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static ConcurrentHashMap<String, ChannelInfo> channelInfoMap = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        logger.info("===========================");
        RequestMessage requestMessage = (RequestMessage)msg;
        switch (requestMessage.getRequestHeader().getMessageType()) {
            case BIND: {
                System.out.println(ctx.channel().remoteAddress().toString());
                logger.info("a new client -" + ctx.channel().remoteAddress() + " /log in...");
                channelInfoMap.put(ctx.channel().remoteAddress().toString(), new ChannelInfo(ctx.channel()
                        , requestMessage.getRequestHeader().getTime()
                        , requestMessage.getRequestHeader().getLocation()));
                ReplyMessage replyMessage = (ReplyMessage) serverMessageFactory.newMessage(ServerMessageType.LOGIN_REPLY);
                replyMessage.setTime(System.currentTimeMillis());
                replyMessage.setReplyMessage("login succeed...");
                ctx.channel().writeAndFlush(replyMessage);
                break;
            }
            case NORMAL:
                logger.info("a connected client -" + ctx.channel().remoteAddress() + " /send message");
                channelInfoMap.forEach((key, channelInfo) -> {
                    SchedulingMethod schedulingMethod = new SchedulingMethodDemo();
                    if (key.equals(ctx.channel().remoteAddress().toString())) {
                        ReplyMessage replyMessage = (ReplyMessage) serverMessageFactory.newMessage(ServerMessageType.MULTICAST_REPLY);
                        replyMessage.setTime(System.currentTimeMillis());
                        replyMessage.setReplyMessage("message has been sent to messageBroker...");
                        channelInfo.getChannel().writeAndFlush(replyMessage);

                    } else {
                        try {
                            Class<?> clazz = Class.forName(schedulingMethodName);
                            SchedulingMethod schedulingMethod1 = (SchedulingMethod) clazz.getDeclaredConstructor().newInstance();
                            if (schedulingMethod1.judge(requestMessage, channelInfo)) {
                                RoutingMessage routingMessage = (RoutingMessage) serverMessageFactory.newMessage(ServerMessageType.MULTICAST);
                                routingMessage.setRequestMessage(requestMessage);
                                routingMessage.setTime(System.currentTimeMillis());
                                channelInfo.getChannel().writeAndFlush(routingMessage);
                            }
                        } catch (ClassNotFoundException | InstantiationException
                                | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case UNBIND: {
                logger.info("a connected client -" + ctx.channel().remoteAddress() + " /log out...");
                channelInfoMap.remove(ctx.channel().remoteAddress().toString());
                ReplyMessage replyMessage = (ReplyMessage) serverMessageFactory.newMessage(ServerMessageType.LOGOUT_REPLY);
                replyMessage.setTime(System.currentTimeMillis());
                replyMessage.setReplyMessage("log out server...");
                ctx.channel().writeAndFlush(replyMessage);
                break;
            }
        }
        logger.info("连接数: " + channelInfoMap.size() + "\n");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        channelGroup.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        channelGroup.remove(ctx.channel());
    }

    public Boolean checkConnection() {
        if (channelInfoMap.size() != channelGroup.size())
            return false;
        else
            return true;
    }
}