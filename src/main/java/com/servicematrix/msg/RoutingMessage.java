package com.servicematrix.msg;

import java.text.SimpleDateFormat;

public class RoutingMessage extends ServerMessage {
    private long time;

    private ClientMessage clientMessage;

    public RoutingMessage(String serverId) {
        super(serverId);
    }

    public RoutingMessage(String serverId, long time, ClientMessage clientMessage) {
        super(serverId);
        this.time = time;
        this.clientMessage = clientMessage;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ClientMessage getClientMessage() {
        return clientMessage;
    }

    public void setClientMessage(ClientMessage clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public String showMessage() {
        return "RoutingMessage{" +
                "time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time) +
                ", clientMessage=" + clientMessage.toString() +
                "} " + super.toString();
    }
}
