package com.servicematrix.scheduling;

import com.servicematrix.msg.RequestMessage;
import com.servicematrix.msg.Location;
import com.servicematrix.server.ChannelInfo;

public class SchedulingMethodDemo implements SchedulingMethod {

    @Override
    public Boolean judge(RequestMessage requestMessage, ChannelInfo channelInfo) {
        if (Math.abs((requestMessage.getRequestHeader().getTime() - channelInfo.getTime()) / 1000) > 100) {
            return false;
        }
        Location clientLoc = requestMessage.getRequestHeader().getLocation();
        Location desLoc = channelInfo.getLocation();

        double s = Math.pow(Math.abs(clientLoc.getX() - desLoc.getX()), 2) + Math.pow(Math.abs(clientLoc.getY() - desLoc.getY()), 2)
                + Math.pow(Math.abs(clientLoc.getZ() - desLoc.getZ()), 2);
        double distance = Math.pow(s, 0.5);
        return !(distance > 10.00);
    }
}
