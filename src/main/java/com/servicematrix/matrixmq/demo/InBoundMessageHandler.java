package com.servicematrix.matrixmq.demo;

import com.servicematrix.matrixmq.client.MessageConsumer;
import com.servicematrix.matrixmq.msg.client.RequestMessage;
import com.servicematrix.matrixmq.msg.client.ResponseMessage;

public class InBoundMessageHandler extends MessageConsumer {
    @Override
    public RequestMessage checkRequestMessage(RequestMessage requestMessage) {
        System.out.println(requestMessage.toString());
        return null;
    }

    @Override
    public void checkBindMessage(ResponseMessage responseMessage) {
        System.out.println(responseMessage.toString());
    }
}
