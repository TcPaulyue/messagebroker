package com.servicematrix.matrixmq.client;

import com.servicematrix.matrixmq.msg.client.*;
import org.apache.log4j.Logger;

import java.util.UUID;

public class MQClient {

    private static final Logger logger = Logger.getLogger(MQClient.class);

    private MQClientFactory mqClientFactory;

    private volatile boolean isConnected = false;

    private volatile boolean isRunning = false;

    private Location location;

    private String clientId;

    private String msgId = UUID.randomUUID().toString().replace("-","");

    //private String msgId = "demo_2";

    public MQClient(String host, int port,MessageConsumer messageConsumer) {
        mqClientFactory = new MQClientFactory(host,port);
        mqClientFactory.setChannelInboundHandler(new MessageHandler(messageConsumer));
    }

    public MQClient(Location location, String clientId, String host, int port,MessageConsumer messageConsumer) {
        this.location = location;
        this.clientId = clientId;
        mqClientFactory = new MQClientFactory(host,port);
        mqClientFactory.setChannelInboundHandler(new MessageHandler(messageConsumer));
    }

    public void init(){
        mqClientFactory.connect();
        //isConnected = true;
        isRunning = true;
    }

    public boolean bind(){
        ConnectMessage bindMessage = new ConnectMessage();
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setMsgId(msgId);
        requestHeader.setLocation(location);
        requestHeader.setTime(System.currentTimeMillis());
        bindMessage.setRequestHeader(requestHeader);
        bindMessage.setRequestBody(new RequestBody("bind".getBytes()));
        mqClientFactory.getMessageChannel().channel().writeAndFlush(bindMessage).addListener(future -> {
            isConnected = future.isSuccess();
        });
        while(!isConnected){
            logger.info("wait for connecting...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return isConnected;
    }

    public boolean unbind(){
        DisConnectMessage disConnectMessage = new DisConnectMessage();
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setMsgId(msgId);
        requestHeader.setLocation(location);
        requestHeader.setTime(System.currentTimeMillis());
        disConnectMessage.setRequestHeader(requestHeader);
        disConnectMessage.setRequestBody(new RequestBody("unbind".getBytes()));
        mqClientFactory.getMessageChannel().channel().writeAndFlush(disConnectMessage).addListener(future -> {
            if(future.isSuccess())
                isConnected = false;
        });
        while(isConnected){
            logger.info("wait for disconnecting...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return isConnected;
    }

    public void sendAppCtxMsg(RequestMessage msg){
        if(!isConnected || !isRunning)
            return;
        msg.getRequestHeader().setMsgId(msgId);
        msg.getRequestHeader().setLocation(location);
        msg.getRequestHeader().setTime(System.currentTimeMillis());
        mqClientFactory.getMessageChannel().channel().writeAndFlush((AppContextMessage)msg);
    }

    public void sendMessage(RequestMessage msg){
        if(!isConnected || !isRunning)
                return;
        msg.getRequestHeader().setMsgId(msgId);
        msg.getRequestHeader().setLocation(location);
        msg.getRequestHeader().setTime(System.currentTimeMillis());
        mqClientFactory.getMessageChannel().channel().writeAndFlush((PublishMessage)msg);
    }

    public void shutdown(){
        if(isRunning){
            isRunning = false;
            mqClientFactory.close();
            isConnected = false;
        }
    }

    public String getMsgId() {
        return msgId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isConnected() {
        return isConnected;
    }

//    public void setConnected(boolean connected) {
//        isConnected = connected;
//    }

}
