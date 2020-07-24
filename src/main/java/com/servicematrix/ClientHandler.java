package com.servicematrix;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        for(int i=0;i<10;i++){
        RequestData msg = new RequestData();
        msg.setIntValue(i);
        msg.setStringValue("all work and no play makes jack a dull boy");
        ctx.writeAndFlush(msg);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println((ResponseData)msg);
        System.out.println(((ResponseData) msg).getIntValue());
       // ctx.close();
    }
}
