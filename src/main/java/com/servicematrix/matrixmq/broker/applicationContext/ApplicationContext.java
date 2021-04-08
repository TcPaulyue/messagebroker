package com.servicematrix.matrixmq.broker.applicationContext;

import com.servicematrix.matrixmq.broker.clientCluster.RemoteClientCluster;
import com.servicematrix.matrixmq.broker.clientCluster.RemoteClient;
import com.servicematrix.matrixmq.msg.client.RequestMessage;
import io.netty.channel.ChannelId;
import io.netty.util.internal.ConcurrentSet;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ApplicationContext {
    private String sceneId;
    
    private Set<ChannelId> channelIdSet = new ConcurrentSet<>();
    
    private Queue<RequestMessage> messageQueue = new ConcurrentLinkedQueue<>();

    public ApplicationContext(String sceneId) {
        this.sceneId = sceneId;
    }

    public ApplicationContext(String sceneId, Set<ChannelId> channelIdSet, Queue<RequestMessage> messageQueue) {
        this.sceneId = sceneId;
        this.channelIdSet = channelIdSet;
        this.messageQueue = messageQueue;
    }

    public Set<ChannelId> getchannelIdSet() {
        return channelIdSet;
    }

    public synchronized void addchannelId(ChannelId channelId) {
        this.channelIdSet.add(channelId);
    }

    public synchronized void deleteChannelId(ChannelId channelId){
        this.channelIdSet.remove(channelId);
    }
    
    public  Queue<RequestMessage> getMessageQueue() {
        return messageQueue;
    }

    public  boolean pushRequestMessage(RequestMessage requestMessage){
        return messageQueue.offer(requestMessage);
    }

    public  RequestMessage pollRequestMessage(){
        return messageQueue.poll();
    }

    public synchronized List<RemoteClient> getRemoteClients(){
        List<RemoteClient> remoteClients = new ArrayList<>();
        this.channelIdSet.forEach(channelId -> {
            if(RemoteClientCluster.getClientCluster().containsKey(channelId)){
                remoteClients.add(RemoteClientCluster.getClientCluster().get(channelId));
            }
        });
        return remoteClients;
    }
}
