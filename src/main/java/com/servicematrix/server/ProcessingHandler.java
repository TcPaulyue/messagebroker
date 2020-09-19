package com.servicematrix.server;


import com.alibaba.fastjson.JSONObject;
import com.servicematrix.msg.*;
import com.servicematrix.scheduling.SchedulingMethod;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.log4j.Logger;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.servicematrix.msg.RequestMessageType.ACCESSIBLE_MAP;
import static com.servicematrix.msg.RequestMessageType.ENGINE;


public class ProcessingHandler extends ChannelInboundHandlerAdapter {

    private ServerMessageFactory serverMessageFactory;

    private static final Logger logger = Logger.getLogger(ProcessingHandler.class);


    public ProcessingHandler(ServerMessageFactory serverMessageFactory) {
        this.serverMessageFactory = serverMessageFactory;
    }

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static ConcurrentHashMap<String, ChannelInfo> channelInfoMap = new ConcurrentHashMap<>();

    private static Map<String, Map<String, Boolean>> accessibleMap = new HashMap<>();

    private static Channel mapEngineChannel;

    private Boolean is_send_Map_to_Engine(ChannelHandlerContext ctx, RequestMessage requestMessage) {
        if (requestMessage.getRequestHeader().getMessageType().equals(ENGINE) || requestMessage.getRequestHeader().getMessageType().equals(ACCESSIBLE_MAP))
            return false;
        if (!channelInfoMap.keySet().contains(requestMessage.getKey())) {
            channelInfoMap.put(requestMessage.getKey(), new ChannelInfo(ctx.channel()
                    , requestMessage.getRequestHeader().getTopic()
                    , requestMessage.getRequestHeader().getTime()
                    , requestMessage.getRequestHeader().getLocation()));
            return true;
        } else {
            Location location = channelInfoMap.get(requestMessage.getKey()).getLocation();
            return !location.equals(requestMessage.getRequestHeader().getLocation());
        }
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {


        RequestMessage requestMessage = (RequestMessage) msg;

        if (is_send_Map_to_Engine(ctx, requestMessage)) {
            JSONObject jsonObject = new JSONObject();
            channelInfoMap.forEach((key, value) -> jsonObject.put(key, value.getLocation()));
            ReplyMessage replyMessage = (ReplyMessage) serverMessageFactory.newMessage(ServerMessageType.ACCESSIBLE_MAP);
            replyMessage.setReplyMessage(jsonObject.toJSONString());
            mapEngineChannel.writeAndFlush(replyMessage);
        }
        switch (requestMessage.getRequestHeader().getMessageType()) {
            case ENGINE: {
                logger.info("===========================");
                logger.info("AccessibleJudgmentEngine join in....");
                mapEngineChannel = ctx.channel();
                break;
            }
            case ACCESSIBLE_MAP: {
                RequestAccessibleMessage requestAccessibleMessage = (RequestAccessibleMessage) ((RequestMessage) msg).getRequestBody();
                accessibleMap = requestAccessibleMessage.getAccessibleMap();
//                System.out.println(accessibleMap.toString());
            }
            case BIND: {
                logger.info("a new client -" + ctx.channel().remoteAddress() + " /log in...");
                channelInfoMap.put(ctx.channel().remoteAddress().toString(), new ChannelInfo(ctx.channel()
                        , requestMessage.getRequestHeader().getTopic()
                        , requestMessage.getRequestHeader().getTime()
                        , requestMessage.getRequestHeader().getLocation()));
                //this.writeMsgToFile(channelInfoMap.get(ctx.channel().remoteAddress().toString()));
                ReplyMessage replyMessage = (ReplyMessage) serverMessageFactory.newMessage(ServerMessageType.LOGIN_REPLY);
                replyMessage.setTime(System.currentTimeMillis());
                replyMessage.setReplyMessage("login succeed...");
                ctx.channel().writeAndFlush(replyMessage);
                break;
            }
            case NORMAL:
                logger.info("a connected client -" + ctx.channel().remoteAddress() + " /send message");
                channelInfoMap.forEach((key, channelInfo) -> {
                    if (key.equals(ctx.channel().remoteAddress().toString())) {
                        ReplyMessage replyMessage = (ReplyMessage) serverMessageFactory.newMessage(ServerMessageType.MULTICAST_REPLY);
                        replyMessage.setTime(System.currentTimeMillis());
                        replyMessage.setReplyMessage("message has been sent to messageBroker...");
                        channelInfo.getChannel().writeAndFlush(replyMessage);
                    } else {
                        if (channelInfo.getTopic().equals(((RequestMessage) msg).getRequestHeader().getTopic())) {
                            if (accessibleMap.get(channelInfo.getTopic()).get(((RequestMessage) msg).getRequestHeader().getTopic())) {
                                RoutingMessage routingMessage = (RoutingMessage) serverMessageFactory.newMessage(ServerMessageType.MULTICAST);
                                routingMessage.setRequestMessage(requestMessage);
                                routingMessage.setTime(System.currentTimeMillis());
                                channelInfo.getChannel().writeAndFlush(routingMessage);
                            } else {
                                logger.info(((RequestMessage) msg).getKey() + "can not access to " + channelInfo.getChannel().remoteAddress());
                            }
                        }
//                        try {
//                            Class<?> clazz = Class.forName(schedulingMethodName);
//                            SchedulingMethod schedulingMethod1 = (SchedulingMethod) clazz.getDeclaredConstructor().newInstance();
//                            if (schedulingMethod1.judge(requestMessage, channelInfo)) {
//                                RoutingMessage routingMessage = (RoutingMessage) serverMessageFactory.newMessage(ServerMessageType.MULTICAST);
//                                routingMessage.setRequestMessage(requestMessage);
//                                routingMessage.setTime(System.currentTimeMillis());
//                                channelInfo.getChannel().writeAndFlush(routingMessage);
//                            }
//                        } catch (ClassNotFoundException | InstantiationException
//                                | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
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