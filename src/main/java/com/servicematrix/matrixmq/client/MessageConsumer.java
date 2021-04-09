package com.servicematrix.matrixmq.client;

import com.servicematrix.matrixmq.broker.protocol.Publish;
import com.servicematrix.matrixmq.msg.BaseMessage;
import com.servicematrix.matrixmq.msg.broker.AckDisConnectMessage;
import com.servicematrix.matrixmq.msg.broker.BrokerMessage;
import com.servicematrix.matrixmq.msg.client.PublishMessage;
import com.servicematrix.matrixmq.msg.client.RequestMessage;
import org.apache.log4j.Logger;

public abstract class MessageConsumer {

    private static final Logger logger = Logger.getLogger(MessageConsumer.class);

    BaseMessage handleMessage(BaseMessage baseMessage){
        BaseMessage response = null;
        logger.info("messageConsumer:    "+baseMessage.toString());
        switch (baseMessage.getMessageType()){
            case PUBLISH:
                response = this.checkRequestMessage((PublishMessage) baseMessage);
                break;
            case BROKER_ACK_CONNECT:
                this.checkBindMessage((BrokerMessage)baseMessage);
                break;
            case BROKER_ACK_DISCONNECT:
                this.checkDisConnectMsg((AckDisConnectMessage)baseMessage );
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + baseMessage.getMessageType());
        }
        return response;
    }

    public abstract PublishMessage checkRequestMessage(PublishMessage publishMessage);

    //todo :bug
    public abstract void checkBindMessage(BrokerMessage brokerMessage);

    public abstract void checkDisConnectMsg(AckDisConnectMessage ackDisConnectMessage);
}
