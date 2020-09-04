package com.servicematrix.server;

import com.servicematrix.msg.Location;
import io.netty.channel.Channel;


public class ChannelInfo {

    private Channel channel;

    private String topic;

    private long time;

    private Location location;

    public ChannelInfo(Channel channel, long time, Location location) {
        this.channel = channel;
        this.time = time;
        this.location = location;
    }

    public ChannelInfo(Channel channel, String topic, long time, Location location) {
        this.channel = channel;
        this.topic = topic;
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
                ", topic='" + topic + '\'' +
                ", time=" + time +
                ", location=" + location +
                '}';
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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
