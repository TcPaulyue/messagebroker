package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.broker.applicationContext.ApplicationContextCluster;
import com.servicematrix.matrixmq.broker.clientCluster.RemoteClientCluster;
import com.servicematrix.matrixmq.msg.BaseMessage;
import com.servicematrix.matrixmq.msg.client.AppContextMessage;
import com.servicematrix.matrixmq.msg.client.BindMessage;
import com.servicematrix.matrixmq.msg.client.Request;
import com.servicematrix.matrixmq.msg.client.UnBindMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MessageBrokerHandler extends ChannelInboundHandlerAdapter {

    public static ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BaseMessage message = (BaseMessage)msg;
        switch (message.getMessageType()){
            case BIND:
                BindMessage bindMessage = (BindMessage)message;
                bindMessage.setChannelId(ctx.channel().id());
                RemoteClientCluster.addClient(bindMessage,ctx.channel());
                AckBindQueue.pushBindMessage(bindMessage);
                break;
            case APPLICATIONCONTEXT:
                AppContextMessage appContextMessage = (AppContextMessage)message;
                appContextMessage.setChannelId(ctx.channel().id());
//                if(!RemoteClientCluster.getClientCluster().containsKey(ctx.channel().id()) ||
//                        !RemoteClientCluster.getClientCluster().get(ctx.channel().id()).isConnected())
//                    break;
                if(ApplicationContextCluster.addApplicationContext(appContextMessage,ctx.channel())){
                    executorService.execute(new RequestMessageController(appContextMessage.getAppContextId()));
                }
                break;
            case REQUEST:
                Request request = (Request)message;
           //     LevelOneMessageCache.pushRequestMessage(request);
                ApplicationContextCluster.getApplicationContextMap().get(request.getAppContextId())
                        .pushRequestMessage(request);
                break;
            case UNBIND:
                UnBindMessage unBindMessage = (UnBindMessage)message;
                RemoteClientCluster.removeClient(unBindMessage,ctx.channel());

                break;
        }
        //ctx.writeAndFlush(message);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        System.out.println("new client "+ctx.channel().remoteAddress().toString());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
}
