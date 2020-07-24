package com.servicematrix;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProcessingHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        RequestData requestData = (RequestData) msg;

        System.out.println(requestData.getIntValue()+"  "+requestData.getStringValue());

        ResponseData responseData = new ResponseData();

        responseData.setIntValue(requestData.getIntValue() * 2);

        ChannelFuture future = ctx.writeAndFlush(responseData);

        //future.addListener(ChannelFutureListener.CLOSE);

    }
}