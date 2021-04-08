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

    private static Map<ChannelId,RemoteClientInfo> clientCluster = new ConcurrentHashMap<>();

    public static Map<ChannelId, RemoteClientInfo> getClientCluster() {
        return clientCluster;
    }

    public static void setClientCluster(Map<ChannelId, RemoteClientInfo> clientCluster) {
        RemoteClientCluster.clientCluster = clientCluster;
    }

    public static synchronized void addClient(BindMessage bindMessage, Channel channel){
        RemoteClientInfo remoteClientInfo = new RemoteClientInfo(bindMessage.getRequestHeader().getLocation(), channel);
        remoteClientInfo.setConnected(true);
        if(!clientCluster.containsKey(channel.id())){
            clientCluster.put(channel.id(),remoteClientInfo);
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

    public static synchronized List<RemoteClientInfo> getConnectedClientList(){
        List<RemoteClientInfo> remoteClientInfos = new ArrayList<>();
        clientCluster.forEach((key,value)->{
            if(value.isConnected()){
                remoteClientInfos.add(value);
            }
        });
        return remoteClientInfos;
    }

    public static Channel getChannel(ChannelId channelId){
        return clientCluster.get(channelId).getChannel();
    }
}
