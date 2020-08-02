package com.servicematrix.msg;

public abstract class ServerMessage {
    private String serverId;

    public ServerMessage(String serverId) {
        this.serverId = serverId;
    }

    public ServerMessage() {
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public abstract String showMessage();

    @Override
    public String toString() {
        return "ServerMessage{" +
                "serverId='" + serverId + '\'' +
                '}';
    }
}
