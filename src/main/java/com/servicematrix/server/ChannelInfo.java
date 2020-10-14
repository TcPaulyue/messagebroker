package com.servicematrix.server;

import com.servicematrix.msg.Location;
import io.netty.channel.Channel;


public class ChannelInfo {

    private Channel channel;

    private String key;

    private long time;

    private Location location;

    public ChannelInfo(Channel channel, long time, Location location) {
        this.channel = channel;
        this.time = time;
        this.location = location;
    }

    public ChannelInfo(Channel channel, String key, long time, Location location) {
        this.channel = channel;
        this.key = key;
        this.time = time;
        this.location = location;
    }

    public ChannelInfo(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "ChannelInfo{" +
                ", channel=" + channel +
                ", key='" + key + '\'' +
                ", time=" + time +
                ", location=" + location +
                '}';
    }

    public String getkey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
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
