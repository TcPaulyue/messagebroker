package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.msg.client.Location;
import io.netty.channel.Channel;


public class RemoteClientInfo {
    private Channel channel;
    private boolean isConnected;
    private Location location;
    private String topic;

    public RemoteClientInfo(Location location, String topic,Channel channel) {
        this.location = location;
        this.topic = topic;
        this.channel = channel;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
