package com.servicematrix.matrixmq.msg.client;

import com.servicematrix.matrixmq.msg.MessageType;
import io.netty.channel.ChannelId;

public class BindMessage extends RequestMessage {

    public BindMessage(RequestHeader requestHeader, RequestBody requestBody) {
        super(requestHeader, requestBody);
        this.setMessageType(MessageType.BIND);
    }

    public BindMessage() {
        this.setMessageType(MessageType.BIND);
    }


    @Override
    public String toString() {
        return "BindMessage{} " + super.toString();
    }
}
