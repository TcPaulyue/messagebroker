package com.servicematrix.matrixmq.msg.client;

import com.servicematrix.matrixmq.msg.BaseMessage;
import com.servicematrix.matrixmq.msg.MessageType;

public class ResponseMessage extends BaseMessage {
    private String msgId;
    private int status;
    private String ack;

    public ResponseMessage() {
        this.setMessageType(MessageType.RESPONSE);
    }

    public ResponseMessage(String msgId, int status, String ack) {
        this.msgId = msgId;
        this.status = status;
        this.ack = ack;
        this.setMessageType(MessageType.RESPONSE);
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
