package com.servicematrix.matrixmq.broker.clientCluster;

import com.servicematrix.matrixmq.msg.client.BindMessage;
import com.servicematrix.matrixmq.msg.client.UnBindMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RemoteClientCluster {

    private static Map<ChannelId, RemoteClient> clientCluster = new ConcurrentHashMap<>();

    public static Map<ChannelId, RemoteClient> getClientCluster() {
        return clientCluster;
    }

    public static void setClientCluster(Map<ChannelId, RemoteClient> clientCluster) {
        RemoteClientCluster.clientCluster = clientCluster;
    }

    public static synchronized void addClient(BindMessage bindMessage, Channel channel){
        RemoteClient remoteClient = new RemoteClient(bindMessage.getRequestHeader().getLocation(), channel);
        remoteClient.setConnected(true);
        if(!clientCluster.containsKey(channel.id())){
            clientCluster.put(channel.id(), remoteClient);
        }else return;
    }

    public static synchronized void removeClient(UnBindMessage unBindMessage,Channel channel){
        if(clientCluster.containsKey(channel.id())){
            clientCluster.remove(channel.id());
        }
    }

    public static synchronized boolean getClientState(Channel channel){
        return clientCluster.get(channel.id()).isConnected();
    }

    public static synchronized void deleteClient(Channel channel){
        clientCluster.get(channel.id()).setConnected(false);
    }

    public static synchronized List<RemoteClient> getConnectedClientList(){
        List<RemoteClient> remoteClients = new ArrayList<>();
        clientCluster.forEach((key,value)->{
            if(value.isConnected()){
                remoteClients.add(value);
            }
        });
        return remoteClients;
    }

    public static Channel getChannel(ChannelId channelId){
        return clientCluster.get(channelId).getChannel();
    }
}
