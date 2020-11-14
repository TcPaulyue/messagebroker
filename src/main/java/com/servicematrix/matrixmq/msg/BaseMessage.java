package com.servicematrix.matrixmq.msg;

public abstract class BaseMessage {
    private MessageType messageType;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "messageType=" + messageType +
                '}';
    }
}
