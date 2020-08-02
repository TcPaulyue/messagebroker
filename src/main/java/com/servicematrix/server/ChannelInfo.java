package com.servicematrix.server;

import com.servicematrix.msg.Location;
import io.netty.channel.Channel;

import java.text.SimpleDateFormat;

public class ChannelInfo {
    private Channel channel;

    private long time;

    private Location location;

    public ChannelInfo(Channel channel, long time, Location location) {
        this.channel = channel;
        this.time = time;
        this.location = location;
    }

    @Override
    public String toString() {
        return "ChannelInfo{" +
                "channel=" + channel +
                ", time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time) +
                ", location=" + location +
                '}';
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
