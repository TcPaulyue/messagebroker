package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.broker.applicationContext.ApplicationContext;
import com.servicematrix.matrixmq.broker.applicationContext.ApplicationContextCluster;
import com.servicematrix.matrixmq.broker.clientCluster.RemoteClientCluster;
import com.servicematrix.matrixmq.broker.clientCluster.RemoteClientInfo;
import com.servicematrix.matrixmq.broker.strategy.BrokerStrategy;
import com.servicematrix.matrixmq.broker.strategy.BrokerStrategyA;
import com.servicematrix.matrixmq.broker.strategy.BrokerStrategyBuilder;
import com.servicematrix.matrixmq.msg.client.RequestMessage;
import io.netty.channel.ChannelId;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RequestMessageController implements Runnable {
    private volatile boolean stopped = false;

    private final ApplicationContext applicationContext;

    public RequestMessageController(String applicationCtxId) {
//        this.brokerStrategyBuilder = brokerStrategyBuilder;
//        this.brokerStrategyList = brokerStrategyList;
        this.applicationContext = ApplicationContextCluster.getApplicationContextMap().get(applicationCtxId);
    }

//    private BrokerStrategyBuilder brokerStrategyBuilder;
//
//    private List<BrokerStrategy> brokerStrategyList;

    public void stop() {
        stopped = true;
    }

    public boolean isStoped() {
        return stopped;
    }

    @Override
    public void run(){
        while(!stopped){
            ApplicationContextCluster.getApplicationContextMap().forEach((s, applicationContext) -> {
                RequestMessage requestMessage = applicationContext.pollRequestMessage();
                if(requestMessage!=null){
                    System.out.println(requestMessage.toString());
                    List<RemoteClientInfo> remoteClientInfos = applicationContext.getRemoteClientInfos();
                    remoteClientInfos.forEach(remoteClientInfo -> {
                        remoteClientInfo.getChannel().writeAndFlush(requestMessage);
                    });
                }
            });
        }
    }

//    @Override
//    public void run() {
//        while(!stopped){
//            RequestMessage requestMessage = applicationContext.pollRequestMessage();
//            if(requestMessage!=null){
//                System.out.println(requestMessage.toString());
//                Set<ChannelId> channelIds = applicationContext.getchannelIdSet();
//
//                if(channelIds.size()<=1){
//                    applicationContext.pushRequestMessage(requestMessage);
//                    continue;
//                }
//                List<RemoteClientInfo> remoteClientInfos = applicationContext.getRemoteClientInfos();
//                remoteClientInfos.forEach(remoteClientInfo -> {
//                    remoteClientInfo.getChannel().writeAndFlush(requestMessage);
//                });
////                BrokerStrategyA brokerStrategyA = new BrokerStrategyA(remoteClientInfos);
////                List<String> keys = brokerStrategyBuilder.getBinderList(requestMessage,brokerStrategyList);
////                ApplicationContextCluster.getChannelsByTopic(topic).forEach((key, value)->{
////                    if(keys.contains(key) && value.isConnected())
////                        value.getChannel().writeAndFlush(requestMessage);
//////                    if(!key.equals(requestMessage.getRequestHeader().getMsgId()) && value.isConnected())
//////                        value.getChannel().writeAndFlush(requestMessage);
////                });
//            }
//        }
//    }
}