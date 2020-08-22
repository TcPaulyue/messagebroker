package com.servicematrix.msg;

import java.text.SimpleDateFormat;

public class ReplyMessage extends ServerMessage {

    private String replyMessage;

    private ServerMessageType serverMessageType;

    private long time;


    public ReplyMessage(ServerMessageType serverMessageType,String serverId) {
        super(serverId);
        this.serverMessageType = serverMessageType;
    }

    @Override
    public String showMessage() {
        return "ReplyMessage{" +
                "replyMessage='" + replyMessage + '\'' +
                ", time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time) +
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
