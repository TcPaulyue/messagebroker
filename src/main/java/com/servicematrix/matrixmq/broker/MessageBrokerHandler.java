package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.msg.BaseMessage;
import com.servicematrix.matrixmq.msg.client.BindMessage;
import com.servicematrix.matrixmq.msg.client.BroadCastRequest;
import com.servicematrix.matrixmq.msg.client.UnBindMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageBrokerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BaseMessage message = (BaseMessage)msg;
        switch (message.getMessageType()){
            case BIND:
                BindMessage bindMessage = (BindMessage)message;
                ClientCluster.addChannel(bindMessage.getRequestHeader().getTopic(),ctx.channel(),bindMessage);
                AckBindQueue.pushBindMessage(bindMessage);
                break;
            case BROADCAST:
                BroadCastRequest broadCastRequest = (BroadCastRequest)message;
                LevelOneMessageCache.pushRequestMessage(broadCastRequest);
                break;
            case UNBIND:
                UnBindMessage unBindMessage = (UnBindMessage)message;
                ClientCluster.removeChannel(unBindMessage.getRequestHeader().getTopic(),unBindMessage);
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
