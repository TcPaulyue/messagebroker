package com.servicematrix.client;


import com.servicematrix.server.ResponseData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if(msg instanceof ResponseData) {
            ResponseData responseData = (ResponseData) msg;
            System.out.println("=============");
            System.out.println(responseData.getIntValue() + "  " + responseData.getMsg());
        }else{
            String reply = (String) msg;
            System.out.println(reply);
        }
       // ctx.close();
    }
}
