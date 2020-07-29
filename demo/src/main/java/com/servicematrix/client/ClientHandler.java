package com.servicematrix.client;

import com.servicematrix.Message;
import com.servicematrix.server.ResponseData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;


public class ClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext ctx;

    private ResponseData response;

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println((ResponseData)msg);
        System.out.println(((ResponseData) msg).getIntValue());
        response = (ResponseData)msg;
        //promise.setSuccess();
        // ctx.close();
    }
//
//    public synchronized ChannelPromise sendMessage(Object message) {
//        System.out.println("111111111");
//        while (ctx == null) {
//            try {
//                TimeUnit.MILLISECONDS.sleep(1);
//                //logger.error("等待ChannelHandlerContext实例化");
//            } catch (InterruptedException e) {
//                System.out.println("等待ChannelHandlerContext实例化过程中出错");
//            }
//        }
//        promise = ctx.newPromise();
//        ctx.writeAndFlush(message);
//        return promise;
//    }
//
    public ResponseData getResponse(){
        return response;
    }
}