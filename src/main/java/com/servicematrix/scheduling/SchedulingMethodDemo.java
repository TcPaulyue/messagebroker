package com.servicematrix.scheduling;

import com.servicematrix.msg.ClientMessage;
import com.servicematrix.msg.Location;
import com.servicematrix.server.ChannelInfo;

public class SchedulingMethodDemo implements SchedulingMethod {

    @Override
    public Boolean judge(ClientMessage clientMessage, ChannelInfo channelInfo) {
        if (Math.abs((clientMessage.getTime() - channelInfo.getTime()) / 1000) > 100) {
            return false;
        }
        Location clientLoc = clientMessage.getLocation();
        Location desLoc = channelInfo.getLocation();

        double s = Math.pow(Math.abs(clientLoc.getX() - desLoc.getX()), 2) + Math.pow(Math.abs(clientLoc.getY() - desLoc.getY()), 2)
                + Math.pow(Math.abs(clientLoc.getZ() - desLoc.getZ()), 2);
        double distance = Math.pow(s, 0.5);
        return !(distance > 10.00);
    }
}
