package com.servicematrix.matrixmq.msg.broker;

import com.servicematrix.matrixmq.msg.BaseMessage;

public abstract class BrokerMessage extends BaseMessage {
    private String brokerId;

    private long time;

    private String msg;

    public BrokerMessage(String brokerId, long time, String msg) {
        this.brokerId = brokerId;
        this.time = time;
        this.msg = msg;
    }

    public BrokerMessage() {
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BrokerMessage{" +
                "brokerId='" + brokerId + '\'' +
                ", time=" + time +
                ", msg='" + msg + '\'' +
                "} " + super.toString();
    }
}
