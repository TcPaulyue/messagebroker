package com.servicematrix.matrixmq.demo;

import com.servicematrix.matrixmq.client.MessageConsumer;
import com.servicematrix.matrixmq.msg.broker.AckDisConnectMessage;
import com.servicematrix.matrixmq.msg.broker.BrokerMessage;
import com.servicematrix.matrixmq.msg.client.PublishMessage;

public class InBoundMessageHandler extends MessageConsumer {

    @Override
    public PublishMessage checkRequestMessage(PublishMessage publishMessage) {
        System.out.println("inboundMessageHandler:  "+publishMessage.toString());
        return null;
    }

    @Override
    public void checkBindMessage(BrokerMessage brokerMessage) {
        System.out.println("inboundMessageHandler:  "+brokerMessage.toString());
    }

    @Override
    public void checkDisConnectMsg(AckDisConnectMessage ackDisConnectMessage) {
        System.out.println("inboundMessageHandler:  "+ackDisConnectMessage.toString());
    }
}
