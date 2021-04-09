package com.servicematrix.matrixmq.broker.clientCluster;

import com.servicematrix.matrixmq.msg.client.ConnectMessage;
import com.servicematrix.matrixmq.msg.client.DisConnectMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RemoteClientCluster {

    private static volatile Map<ChannelId, RemoteClient> clientCluster = new ConcurrentHashMap<>();

    public static Map<ChannelId, RemoteClient> getClientCluster() {
        return clientCluster;
    }

    public static void setClientCluster(Map<ChannelId, RemoteClient> clientCluster) {
        RemoteClientCluster.clientCluster = clientCluster;
    }

    public static synchronized void addClient(ConnectMessage connectMessage, Channel channel){
        RemoteClient remoteClient = new RemoteClient(connectMessage.getRequestHeader().getLocation(), channel);
        remoteClient.setConnected(true);
        if(!clientCluster.containsKey(channel.id())){
            clientCluster.put(channel.id(), remoteClient);
        }else return;
    }

    public static synchronized int getClientsSize(){
        return clientCluster.size();
    }

    public static synchronized boolean containsClient(Channel channel){
        return clientCluster.containsKey(channel.id());
    }
    public static synchronized void removeClient(DisConnectMessage disConnectMessage, Channel channel){
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

    public static synchronized Channel getChannel(ChannelId channelId){
        Channel channel = clientCluster.get(channelId).getChannel();
        if(channel!=null)
            return clientCluster.get(channelId).getChannel();
        return null;
    }
}
