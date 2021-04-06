package com.servicematrix.matrixmq.broker.applicationContext;

import com.servicematrix.matrixmq.msg.client.AppContextMessage;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContextCluster {

    private static Map<String,ApplicationContext> applicationContextMap = new ConcurrentHashMap<>();

    public static Map<String, ApplicationContext> getApplicationContextMap() {
        return applicationContextMap;
    }

    public static void setApplicationContextMap(Map<String, ApplicationContext> applicationContextMap) {
        ApplicationContextCluster.applicationContextMap = applicationContextMap;
    }

    public synchronized static boolean addApplicationContext(AppContextMessage appContextMessage,Channel channel){
        if(!applicationContextMap.containsKey(appContextMessage.getAppContextId())){
            applicationContextMap.put(appContextMessage.getAppContextId(),new ApplicationContext(appContextMessage.getAppContextId()));
            applicationContextMap.get(appContextMessage.getAppContextId()).addchannelId(channel.id());
            return true;
        }else{
            applicationContextMap.get(appContextMessage.getAppContextId()).addchannelId(channel.id());
            return false;
        }
    }

    public synchronized static void deleteApplicationContext(String applicationContextId){
        applicationContextMap.remove(applicationContextId);
    }

}
