package com.servicematrix.matrixmq.client;

import com.servicematrix.matrixmq.msg.BaseMessage;
import com.servicematrix.matrixmq.msg.broker.BrokerMessage;
import com.servicematrix.matrixmq.msg.client.RequestMessage;
import org.apache.log4j.Logger;

public abstract class MessageConsumer {

    private static final Logger logger = Logger.getLogger(MessageConsumer.class);

    BaseMessage handleMessage(BaseMessage baseMessage){
        BaseMessage response = null;
        logger.info("messageConsumer:    "+baseMessage.toString());
        switch (baseMessage.getMessageType()){
            case REQUEST:
                response = this.checkRequestMessage((RequestMessage)baseMessage);
                break;
            case BROKER_ACK_BIND:
                this.checkBindMessage((BrokerMessage)baseMessage);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + baseMessage.getMessageType());
        }
        return response;
    }

    public abstract RequestMessage checkRequestMessage(RequestMessage requestMessage);

    //todo :bug
    public abstract void checkBindMessage(BrokerMessage brokerMessage);

}
