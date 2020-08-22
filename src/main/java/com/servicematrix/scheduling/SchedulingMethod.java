package com.servicematrix.scheduling;

import com.servicematrix.msg.RequestMessage;
import com.servicematrix.server.ChannelInfo;

public interface SchedulingMethod {
    Boolean judge(RequestMessage requestMessage, ChannelInfo channelInfo);
}
