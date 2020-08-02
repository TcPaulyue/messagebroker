package com.servicematrix.client.netty;


import com.servicematrix.msg.ServerMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if(msg instanceof ServerMessage) {
            ServerMessage responseMsg = (ServerMessage) msg;
            System.out.println("=============");
            System.out.println(responseMsg.showMessage());
        }else{
            String reply = (String) msg;
            System.out.println(reply);
        }

    }
}
