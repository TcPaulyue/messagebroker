package com.servicematrix.matrixmq.broker;


import com.servicematrix.matrixmq.broker.clientCluster.RemoteClientCluster;
import com.servicematrix.matrixmq.msg.broker.AckConnectMessage;
import com.servicematrix.matrixmq.msg.client.ConnectMessage;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;


import static com.servicematrix.matrixmq.broker.MQBroker.BROKERID;

public class AckConnectController implements Runnable {

    private static final Logger logger = Logger.getLogger(AckConnectController.class);
    
    private volatile boolean stopped = false;

    public void stop() {
        stopped = true;
    }

    public boolean isStoped() {
        return stopped;
    }

    @Override
    public void run() {
        while(!stopped){
            ConnectMessage connectMessage = AckConnectQueue.getConnectMessage();
            if(connectMessage!=null){
                logger.info("Thread-AckConnect "+connectMessage.toString());
                Channel channel = RemoteClientCluster.getChannel(connectMessage.getChannelId());
                if(channel!=null){
                    AckConnectMessage ackConnectMessage = new AckConnectMessage();
                    ackConnectMessage.setBrokerId(BROKERID);
                    ackConnectMessage.setTime(System.currentTimeMillis());
                    channel.writeAndFlush(ackConnectMessage).addListener(future -> {
                        if(future.isSuccess()){
                            logger.info("Thread-AckBind "+ ackConnectMessage.toString());
                        }
                    });
                }
                else AckConnectQueue.pushConnectMessage(connectMessage);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
