package com.servicematrix.matrixmq.broker.clientCluster;

import com.servicematrix.matrixmq.msg.client.Location;
import io.netty.channel.Channel;


public class RemoteClient {
    private Channel channel;
    private boolean isConnected;
    private Location location;

    public RemoteClient(Location location, Channel channel) {
        this.location = location;
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

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
