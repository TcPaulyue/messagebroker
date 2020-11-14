package com.servicematrix.matrixmq.client;

import com.servicematrix.matrixmq.msg.client.*;

import java.util.Collections;
import java.util.UUID;

public class MQClient {

    private MQClientFactory mqClientFactory;

    private boolean isConnected = false;

    private boolean isRunning = false;

    private Location location;

    private String topic;

    private String host;

    private int port;

    private String msgId = UUID.randomUUID().toString().replace("-","");


    public MQClient(String host, int port,MessageConsumer messageConsumer) {
        this.host = host;
        this.port = port;
        mqClientFactory = new MQClientFactory(host,port);
        mqClientFactory.setChannelInboundHandler(new MessageHandler(messageConsumer));
    }

    public MQClient(Location location, String topic, String host, int port,MessageConsumer messageConsumer) {
        this.location = location;
        this.topic = topic;
        this.host = host;
        this.port = port;
        mqClientFactory = new MQClientFactory(host,port);
        mqClientFactory.setChannelInboundHandler(new MessageHandler(messageConsumer));
    }

    public void init(){
        mqClientFactory.connect();
        //isConnected = true;
        isRunning = true;
    }

    public void bind(){
        BindMessage bindMessage = new BindMessage();
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setTopic(topic);
        requestHeader.setMsgId(msgId);
        requestHeader.setLocation(location);
        requestHeader.setTime(System.currentTimeMillis());
        bindMessage.setRequestHeader(requestHeader);
        bindMessage.setRequestBody(new RequestBody("bind".getBytes()));
        mqClientFactory.getMessageChannel().channel().writeAndFlush(bindMessage);
        isConnected = true;
    }

    public String getMsgId() {
        return msgId;
    }

    public void unbind(){
        UnBindMessage unBindMessage = new UnBindMessage();
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setTopic(topic);
        requestHeader.setMsgId(msgId);
        requestHeader.setLocation(location);
        requestHeader.setTime(System.currentTimeMillis());
        unBindMessage.setRequestHeader(requestHeader);
        unBindMessage.setRequestBody(new RequestBody("unbind".getBytes()));
        mqClientFactory.getMessageChannel().channel().writeAndFlush(unBindMessage);
        isConnected = false;
    }

    public void sendMessage(String msg){
        if(!isConnected || !isRunning)
                return;
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setTopic(topic);
        requestHeader.setMsgId(msgId);
        requestHeader.setLocation(location);
        requestHeader.setTime(System.currentTimeMillis());
        BroadCastRequest broadCastRequest = new BroadCastRequest(requestHeader,new RequestBody(msg.getBytes()));
        broadCastRequest.setTopics(Collections.singletonList(topic));
        mqClientFactory.getMessageChannel().channel().writeAndFlush(broadCastRequest);
    }

    public void shutdown(){
        if(isRunning){
            isRunning = false;
            mqClientFactory.close();
            isConnected = false;
        }
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isConnected() {
        return isConnected;
    }

//    public void setConnected(boolean connected) {
//        isConnected = connected;
//    }

}
