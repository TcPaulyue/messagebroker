package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.broker.applicationContext.ApplicationContextCluster;
import com.servicematrix.matrixmq.broker.clientCluster.RemoteClientCluster;
import com.servicematrix.matrixmq.broker.protocol.ProtocolProcess;
import com.servicematrix.matrixmq.msg.BaseMessage;
import com.servicematrix.matrixmq.msg.client.AppContextMessage;
import com.servicematrix.matrixmq.msg.client.ConnectMessage;
import com.servicematrix.matrixmq.msg.client.DisConnectMessage;
import com.servicematrix.matrixmq.msg.client.PublishMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MessageBrokerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(MessageBrokerHandler.class);

    private ProtocolProcess protocolProcess;

    public MessageBrokerHandler(ProtocolProcess protocolProcess) {
        this.protocolProcess = protocolProcess;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BaseMessage message = (BaseMessage)msg;
        switch (message.getMessageType()){
            case CONNECT:
                logger.info("new client "+ctx.channel().remoteAddress()+" Connect");
                protocolProcess.connect().processConnect(ctx.channel(),(ConnectMessage)message);
                break;
            case APPLICATIONCONTEXT:
                AppContextMessage appContextMessage = (AppContextMessage)message;
                logger.info(ctx.channel().remoteAddress()+" add new appCtx:/ "+ appContextMessage.getAppContextId());
                protocolProcess.subscribe().processSubscribe(ctx.channel(),appContextMessage);
                break;
            case PUBLISH:
                PublishMessage publishMessage = (PublishMessage)message;
                logger.info("appCtx: "+ publishMessage.getAppContextId() + "/  client: "+ctx.channel().remoteAddress()
                    + "/ msg: "+((PublishMessage) message).getRequestBody());
                protocolProcess.publish().processPublish(publishMessage);
                break;
            case DISCONNECT:
                logger.info("Netty:client "+ctx.channel().remoteAddress()+" disConnect...");
                protocolProcess.disConnect().processDisConnect(ctx.channel(),(DisConnectMessage) message);
                break;
        }
        //ctx.writeAndFlush(message);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        //logger.info("new channel "+ctx.channel().remoteAddress().toString());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
}
