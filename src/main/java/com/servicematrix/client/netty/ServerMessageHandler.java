package com.servicematrix.client.netty;


import com.servicematrix.msg.ReplyMessage;
import com.servicematrix.msg.RequestMessage;
import com.servicematrix.msg.RoutingMessage;
import com.servicematrix.msg.ServerMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;


public abstract class ServerMessageHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(ServerMessageHandler.class);

    protected Channel channel;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        this.channel = ctx.channel();
        if(msg instanceof ServerMessage) {
            if(msg instanceof RoutingMessage){
                RoutingMessage routingMessage = (RoutingMessage)msg;
                this.responseMessage(routingMessage.getRequestMessage());
            }else if(msg instanceof ReplyMessage){
                ReplyMessage replyMessage = (ReplyMessage)msg;
                this.ackMessage(replyMessage.getReplyMessage());
            }

        }else{
            String reply = (String) msg;
            logger.info(reply);
        }
    }

    public abstract void responseMessage(RequestMessage requestMessage) throws InterruptedException;

    public abstract void ackMessage(String msg);
}
