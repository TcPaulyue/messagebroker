package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.msg.client.BindMessage;
import com.servicematrix.matrixmq.msg.client.UnBindMessage;
import io.netty.channel.Channel;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ClientCluster {
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, RemoteClientInfo>> topic_Client_Channel_Map = new ConcurrentHashMap<>();


    public static ConcurrentHashMap<String, ConcurrentHashMap<String, RemoteClientInfo>> getTopic_Client_Channel_Map() {
        return topic_Client_Channel_Map;
    }

    public static void setTopic_Client_Channel_Map(ConcurrentHashMap<String, ConcurrentHashMap<String, RemoteClientInfo>> topic_Client_Channel_Map) {
        ClientCluster.topic_Client_Channel_Map = topic_Client_Channel_Map;
    }

    public static void addChannel(String topic, Channel channel, BindMessage bindMessage){
        RemoteClientInfo remoteClientInfo = new RemoteClientInfo(bindMessage.getRequestHeader().getLocation(),topic,channel);
        remoteClientInfo.setConnected(true);
        if(!topic_Client_Channel_Map.containsKey(topic)){
            ConcurrentHashMap<String,RemoteClientInfo> map = new ConcurrentHashMap<>();
            map.put(bindMessage.getRequestHeader().getMsgId(),remoteClientInfo);
            topic_Client_Channel_Map.put(topic,map);
        }else {
            topic_Client_Channel_Map.get(topic).put(bindMessage.getRequestHeader().getMsgId(),remoteClientInfo);
        }
    }

    public static void removeChannel(String topic, UnBindMessage unBindMessage){
        topic_Client_Channel_Map.get(topic).get(unBindMessage.getRequestHeader().getMsgId()).setConnected(false);
    }

    public static void deleteChannel(String topic,Channel channel){
        topic_Client_Channel_Map.get(topic).keySet().forEach(key->{
            if(topic_Client_Channel_Map.get(topic).get(key).getChannel().equals(channel)){
                topic_Client_Channel_Map.get(topic).remove(key);
            }
        });
    }

    public static Channel getChannel(String topic,String msgId){
        if(topic_Client_Channel_Map.get(topic).get(msgId).isConnected())
            return topic_Client_Channel_Map.get(topic).get(msgId).getChannel();
        else return null;
    }

    public static ConcurrentHashMap<String,RemoteClientInfo> getChannelsByTopic(String topic){
        return topic_Client_Channel_Map.get(topic);
    }
}
