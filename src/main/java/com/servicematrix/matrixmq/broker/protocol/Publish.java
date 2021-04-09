package com.servicematrix.matrixmq.broker.protocol;

import com.servicematrix.matrixmq.broker.applicationContext.ApplicationContextCluster;
import com.servicematrix.matrixmq.msg.client.PublishMessage;

public class Publish {

    public void processPublish(PublishMessage publishMessage){
        ApplicationContextCluster.getApplicationContextMap().get(publishMessage.getAppContextId())
                .pushRequestMessage(publishMessage);
    }
}
