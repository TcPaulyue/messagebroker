package com.servicematrix.msg;

import com.servicematrix.server.NettyServer;

public class ServerMessageFactory {

    public ServerMessage newMessage(ServerMessageType serverMessageType) {
        if (serverMessageType.equals(ServerMessageType.LOGIN_REPLY)
                || serverMessageType.equals(ServerMessageType.LOGOUT_REPLY)
                || serverMessageType.equals(ServerMessageType.MULTICAST_REPLY))
            return new ReplyMessage(serverMessageType, NettyServer.serverId);
        else if (serverMessageType.equals(ServerMessageType.MULTICAST))
            return new RoutingMessage(NettyServer.serverId);
        else return null;
    }

}
