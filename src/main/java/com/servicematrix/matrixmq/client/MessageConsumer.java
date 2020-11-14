package com.servicematrix.matrixmq.client;

import com.servicematrix.matrixmq.msg.BaseMessage;
import com.servicematrix.matrixmq.msg.client.RequestMessage;
import com.servicematrix.matrixmq.msg.client.ResponseMessage;

public abstract class MessageConsumer {

    BaseMessage handleMessage(BaseMessage baseMessage){
        BaseMessage response = null;
        System.out.println("messageConsumer    "+baseMessage.toString());
        switch (baseMessage.getMessageType()){
            case BROADCAST:
                response = this.checkRequestMessage((RequestMessage)baseMessage);
                break;
            case BROKER_ACK_BIND:
                this.checkBindMessage((ResponseMessage)baseMessage);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + baseMessage.getMessageType());
        }
        return response;
    }

    public abstract RequestMessage checkRequestMessage(RequestMessage requestMessage);

    public abstract void checkBindMessage(ResponseMessage responseMessage);

}
