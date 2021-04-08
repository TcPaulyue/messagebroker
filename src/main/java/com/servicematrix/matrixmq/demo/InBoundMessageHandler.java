package com.servicematrix.matrixmq.demo;

import com.servicematrix.matrixmq.client.MessageConsumer;
import com.servicematrix.matrixmq.msg.broker.BrokerMessage;
import com.servicematrix.matrixmq.msg.client.RequestMessage;

public class InBoundMessageHandler extends MessageConsumer {
    @Override
    public RequestMessage checkRequestMessage(RequestMessage requestMessage) {
        //System.out.println(requestMessage.toString());
        return null;
    }


    @Override
    public void checkBindMessage(BrokerMessage brokerMessage) {
        //System.out.println(brokerMessage.toString());
    }
}
