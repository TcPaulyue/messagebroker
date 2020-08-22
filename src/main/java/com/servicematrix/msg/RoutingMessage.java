package com.servicematrix.msg;

import java.text.SimpleDateFormat;

public class RoutingMessage extends ServerMessage {
    private long time;

    private RequestMessage requestMessage;

    public RoutingMessage(String serverId) {
        super(serverId);
    }

    public RoutingMessage(String serverId, long time, RequestMessage requestMessage) {
        super(serverId);
        this.time = time;
        this.requestMessage = requestMessage;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public RequestMessage getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(RequestMessage requestMessage) {
        this.requestMessage = requestMessage;
    }

    @Override
    public String showMessage() {
        return "RoutingMessage{" +
                "time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time) +
                ", requestMessage=" + requestMessage.toString() +
                "} " + super.toString();
    }
}
