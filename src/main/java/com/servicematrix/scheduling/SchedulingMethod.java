package com.servicematrix.scheduling;

import com.servicematrix.msg.ClientMessage;
import com.servicematrix.server.ChannelInfo;

public interface SchedulingMethod {
    Boolean judge(ClientMessage clientMessage, ChannelInfo channelInfo);
}
