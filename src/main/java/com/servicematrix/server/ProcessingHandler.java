package com.servicematrix.server;

import com.servicematrix.client.RequestData;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ProcessingHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        RequestData requestData = (RequestData) msg;

        System.out.println(requestData.getCount()+"  "+requestData.getTopic()+" "+requestData.getMessage());

        System.out.println("连接数: "+channelGroup.size());

        Channel ch = ctx.channel();
        System.out.println(ch.config().getOptions().get(ChannelOption.SO_KEEPALIVE));
        channelGroup.forEach(channel -> {
            if(channel== ch){
                System.out.println("==111==   "+channel.remoteAddress());
                ResponseData responseData = new ResponseData();
                responseData.setIntValue(1);
                responseData.setMsg("本人");
                channel.writeAndFlush(responseData);
            }else{
                System.out.println("==222==  "+channel.remoteAddress());
                ResponseData responseData = new ResponseData();
                responseData.setIntValue(2);
                responseData.setMsg("other Client");
                channel.writeAndFlush(responseData);
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();


        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 加入了\n");

        System.out.println(channel.localAddress()+"[服务器] - " + channel.remoteAddress() + " 加入了\n");
        // 先写入到客户端，最后再将自己添加到ChannelGroup中
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 离开了\n");

        // 这里channelGroup会自动进行调用，所以这行代码不写也是可以的。
        channelGroup.remove(channel);

        System.out.println(channel.localAddress()+"[服务器] - " + channel.remoteAddress() + " 离开了\n");

    }
}