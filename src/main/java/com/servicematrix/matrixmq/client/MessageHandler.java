package com.servicematrix.matrixmq.client;

import com.servicematrix.matrixmq.msg.BaseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

public class MessageHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(MessageHandler.class);

    private MessageConsumer messageConsumer;

    public MessageHandler(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BaseMessage message = (BaseMessage)msg;
        logger.info(message);
        BaseMessage result =  messageConsumer.handleMessage(message);
        if(result!=null)
            ctx.writeAndFlush(result).addListener(future -> {
                if(future.isSuccess()){
                    logger.info(result.toString()+" sent to broker...");
                }else{
                    logger.info(result.toString()+ " failed ...");
                }
            });

    }

//    public abstract void responseMessage(RequestMessage requestMessage) throws InterruptedException;
//
//    public abstract void ackMessage(String msg);
}
