package com.servicematrix.msg;

public class ReplyMessage extends ServerMessage {
    private String replyMessage;


    private ServerMessageType serverMessageType;

    private long time;

    public ReplyMessage(String serverId) {
        super(serverId);
    }

    public ReplyMessage(ServerMessageType serverMessageType) {
        this.serverMessageType = serverMessageType;
    }

    @Override
    public String showMessage() {
        return "ReplyMessage{" +
                "replyMessage='" + replyMessage + '\'' +
                ", time=" + time +
                "} " + super.toString();
    }


    @Override
    public String toString() {
        return "ReplyMessage{" +
                "replyMessage='" + replyMessage + '\'' +
                ", time=" + time +
                "} " + super.toString();
    }

    public ReplyMessage(String serverId, String replyMessage, long time) {
        super(serverId);
        this.replyMessage = replyMessage;
        this.time = time;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    public ServerMessageType getServerMessageType() {
        return serverMessageType;
    }

    public void setServerMessageType(ServerMessageType serverMessageType) {
        this.serverMessageType = serverMessageType;
    }

}
