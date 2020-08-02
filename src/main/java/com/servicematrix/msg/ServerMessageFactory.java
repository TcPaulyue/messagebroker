package com.servicematrix.msg;

public class ServerMessageFactory {

    public ServerMessage newMessage(ServerMessageType serverMessageType) {
        if (serverMessageType.equals(ServerMessageType.LOGIN_REPLY) || serverMessageType.equals(ServerMessageType.LOGOUT_REPLY))
            return new ReplyMessage(serverMessageType);
        else if (serverMessageType.equals(ServerMessageType.MULTICAST))
            return new RoutingMessage();
        else return null;
    }

}
