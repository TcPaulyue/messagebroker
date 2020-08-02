package com.servicematrix.msg;

import com.servicematrix.client.MessageHandler;

public class MessageFactory {
    private ClientMessage clientMessage;

    public ClientMessage newMessage(MessageHandler.MessageType messageType
            , String id, long time, Location location
            ){
        switch (messageType) {
            case LOGIN:
                return new LoginClientMessage(id,time,location);
            case TEXT:
                return new NormalClientMessage(id,time,location);
            case LOGOUT:
                return new LogoutClientMessage(id, time,location);
        }
        return null;
    }

}
