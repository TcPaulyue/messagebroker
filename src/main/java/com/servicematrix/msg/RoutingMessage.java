package com.servicematrix.msg;

public class RoutingMessage extends ServerMessage {
    private long time;

    private NormalClientMessage normalMessage;

    public RoutingMessage(String serverId) {
        super(serverId);
    }

    public RoutingMessage() {
    }
    public RoutingMessage(String serverId, long time, NormalClientMessage normalMessage) {
        super(serverId);
        this.time = time;
        this.normalMessage = normalMessage;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public NormalClientMessage getNormalMessage() {
        return normalMessage;
    }

    public void setNormalMessage(NormalClientMessage normalMessage) {
        this.normalMessage = normalMessage;
    }

    @Override
    public String showMessage() {
        return "RoutingMessage{" +
                "time=" + time +
                ", normalMessage=" + normalMessage +
                "} " + super.toString();
    }
}
