package com.servicematrix.server;

import com.servicematrix.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ProcessingHandler extends ChannelInboundHandlerAdapter {


    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {


        Message message = (Message) msg;

        System.out.println(message.getNum()+"  "+message.getTopic());

        System.out.println("连接数: "+channelGroup.size());

        ResponseData responseData = new ResponseData();

        responseData.setIntValue(message.getNum() * 2);

        ChannelFuture future = ctx.writeAndFlush(responseData);

        //future.addListener(ChannelFutureListener.CLOSE);

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 加入了\n");

        // 先写入到客户端，最后再将自己添加到ChannelGroup中
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 离开了\n");

        // 这里channelGroup会自动进行调用，所以这行代码不写也是可以的。
        channelGroup.remove(channel);
    }
}