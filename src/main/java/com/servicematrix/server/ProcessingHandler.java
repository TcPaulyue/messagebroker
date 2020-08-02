package com.servicematrix.server;

import com.servicematrix.msg.*;
import io.netty.channel.Channel;
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
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof LoginClientMessage) {
            System.out.println("a new client log in...");
            LoginClientMessage loginMessage = (LoginClientMessage) msg;
            channelInfoMap.put(loginMessage.getId(), new ChannelInfo(ctx.channel()
                    , ((LoginClientMessage) msg).getTime()
                    , ((LoginClientMessage) msg).getLocation()));

            ReplyMessage replyMessage = (ReplyMessage) serverMessageFactory.newMessage(ServerMessageType.LOGIN_REPLY);
            replyMessage.setServerId(NettyServer.serverId);
            replyMessage.setReplyMessage("login succeed...");
            ctx.channel().writeAndFlush(replyMessage);

        } else if (msg instanceof LogoutClientMessage) {
            System.out.println("a connected client log out...");
            LogoutClientMessage logoutMessage = (LogoutClientMessage) msg;
            channelInfoMap.remove(logoutMessage.getId());

            ReplyMessage replyMessage = (ReplyMessage) serverMessageFactory.newMessage(ServerMessageType.LOGOUT_REPLY);
            replyMessage.setServerId(NettyServer.serverId);
            replyMessage.setReplyMessage("log out server...");

            ctx.channel().writeAndFlush("log out server...");


        } else if (msg instanceof NormalClientMessage) {
            System.out.println("a connected client send message");

            channelInfoMap.forEach((key, channelInfo) -> {
//                if (key.equals(((NormalClientMessage) msg).getId())) {
//                    System.out.println("==111==   " + channelInfo.getChannel().remoteAddress());
//                    ResponseData responseData = new ResponseData();
//                    responseData.setIntValue(1);
//                    responseData.setMsg("本人");
//                    channelInfo.getChannel().writeAndFlush(responseData);
//                } else {
//                    System.out.println("==222==  " + channelInfo.getChannel().remoteAddress());
//                    ResponseData responseData = new ResponseData();
//                    responseData.setIntValue(2);
//                    responseData.setMsg(((NormalClientMessage) msg).getMessage());
//                    channelInfo.getChannel().writeAndFlush(responseData);
//                }
            });
        } else {
            String message = (String) msg;
            System.out.println(message);
        }
        System.out.println("连接数: " + channelInfoMap.size());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();


        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 加入了\n");

        System.out.println(channel.localAddress() + "[服务器] - " + channel.remoteAddress() + " 加入了\n");
        // 先写入到客户端，最后再将自己添加到ChannelGroup中
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 离开了\n");

        // 这里channelGroup会自动进行调用，所以这行代码不写也是可以的。
        channelGroup.remove(channel);

        System.out.println(channel.localAddress() + "[服务器] - " + channel.remoteAddress() + " 离开了\n");

    }
}