package com.servicematrix.matrixmq.msg.client;

import com.servicematrix.matrixmq.msg.MessageType;
import io.netty.channel.ChannelId;

public class ConnectMessage extends RequestMessage {

    public ConnectMessage(RequestHeader requestHeader, RequestBody requestBody) {
        super(requestHeader, requestBody);
        this.setMessageType(MessageType.CONNECT);
    }

    public ConnectMessage() {
        this.setMessageType(MessageType.CONNECT);
    }


    @Override
    public String toString() {
        return "ConnectMessage{} " + super.toString();
    }
}
