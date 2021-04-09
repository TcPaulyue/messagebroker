package com.servicematrix.matrixmq.client;


import com.servicematrix.matrixmq.msg.client.AppContextMessage;
import com.servicematrix.matrixmq.msg.client.PublishMessage;
import com.servicematrix.matrixmq.msg.client.RequestBody;
import com.servicematrix.matrixmq.msg.client.RequestHeader;

public class AppContext {
    private String appContextId;
    private MQClient mqClient;

    public AppContext(String appContextId, MQClient mqClient) {
        this.appContextId = appContextId;
        this.mqClient = mqClient;
    }

    public AppContext(String appContextId) {
        this.appContextId = appContextId;
    }

    public AppContext() {
    }

    public void init(){
        if(mqClient.isConnected()){
            AppContextMessage appContextMessage = new AppContextMessage(new RequestHeader(),new RequestBody("appContext".getBytes()));
            appContextMessage.setAppContextId(appContextId);
            mqClient.sendAppCtxMsg(appContextMessage);
        }
        else{
            return;
        }
    }

    public void sendMessage(String msg){
        PublishMessage publishMessage = new PublishMessage(new RequestHeader(),new RequestBody(msg.getBytes()));
        publishMessage.setAppContextId(appContextId);
        mqClient.sendMessage(publishMessage);
    }

    public void removeAppCtx(){

    }

}
