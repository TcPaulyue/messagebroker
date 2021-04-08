package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.broker.applicationContext.ApplicationContext;
import com.servicematrix.matrixmq.broker.applicationContext.ApplicationContextCluster;
import com.servicematrix.matrixmq.broker.clientCluster.RemoteClient;
import com.servicematrix.matrixmq.broker.strategy.FanoutStrategy;
import com.servicematrix.matrixmq.msg.client.RequestMessage;
import io.netty.channel.ChannelId;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

public class RequestMessageController implements Runnable {
    private volatile boolean stopped = false;

    private static final Logger logger = Logger.getLogger(AckBindController.class);

    private final ApplicationContext applicationContext;

    private final String applicationCtxId;

    public RequestMessageController(String applicationCtxId) {
//        this.brokerStrategyBuilder = brokerStrategyBuilder;
//        this.brokerStrategyList = brokerStrategyList;
        this.applicationCtxId = applicationCtxId;
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

//    @Override
//    public void run(){
//        while(!stopped){
//            ApplicationContextCluster.getApplicationContextMap().forEach((s, applicationContext) -> {
//                RequestMessage requestMessage = applicationContext.pollRequestMessage();
//                if(requestMessage!=null){
//                    System.out.println(requestMessage.toString());
//                    List<RemoteClientInfo> remoteClientInfos = applicationContext.getRemoteClientInfos();
//                    remoteClientInfos.forEach(remoteClientInfo -> {
//                        remoteClientInfo.getChannel().writeAndFlush(requestMessage);
//                    });
//                }
//            });
//        }
//    }

    @Override
    public void run() {
        while(!stopped){
            RequestMessage requestMessage = applicationContext.pollRequestMessage();
            if(requestMessage!=null){
                logger.info("Thread-ReqMsgHandler-"+applicationCtxId+" "+requestMessage.toString());
                Set<ChannelId> channelIds = applicationContext.getchannelIdSet();

                if(channelIds.size()<=1){
                    applicationContext.pushRequestMessage(requestMessage);
                    continue;
                }
                List<RemoteClient> remoteClients = applicationContext.getRemoteClients();
                remoteClients.forEach(remoteClientInfo -> {
                    remoteClientInfo.getChannel().writeAndFlush(requestMessage).addListener(future -> {
                        if(future.isSuccess()){
                            logger.info("Thread-ReqMsgHandler-"+applicationCtxId+" "+"send Msg: "+requestMessage.getRequestHeader().getMsgId()+" to "+remoteClientInfo.getChannel().remoteAddress());
                        }
                    });
                });
//                FanoutStrategy fanoutStrategy = new FanoutStrategy(remoteClients);
//                fanoutStrategy.routing(requestMessage).forEach(channel -> {
//                    channel.writeAndFlush(requestMessage).addListener(future -> {
//                        if(future.isSuccess()){
//                            logger.info("Thread-ReqMsgHandler-"+applicationCtxId+" "+"send Msg: "+requestMessage.getRequestHeader().getMsgId()+" to "+channel.remoteAddress());
//                        }
//                    });
//                });
            }
        }
    }
}
