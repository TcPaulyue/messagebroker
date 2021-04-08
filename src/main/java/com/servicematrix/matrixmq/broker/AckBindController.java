package com.servicematrix.matrixmq.broker;


import com.servicematrix.matrixmq.broker.applicationContext.ApplicationContextCluster;
import com.servicematrix.matrixmq.broker.clientCluster.RemoteClientCluster;
import com.servicematrix.matrixmq.msg.broker.AckBindMessage;
import com.servicematrix.matrixmq.msg.client.BindMessage;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;


import static com.servicematrix.matrixmq.broker.MQBroker.BROKERID;

public class AckBindController implements Runnable {

    private static final Logger logger = Logger.getLogger(AckBindController.class);
    
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
            BindMessage bindMessage = AckBindQueue.getBindMessage();
            if(bindMessage!=null){
                logger.info("Thread-AckBind "+bindMessage.toString());
                Channel channel = RemoteClientCluster.getChannel(bindMessage.getChannelId());
                if(channel!=null){
                    AckBindMessage ackBindMessage = new AckBindMessage();
                    ackBindMessage.setBrokerId(BROKERID);
                    ackBindMessage.setTime(System.currentTimeMillis());
                    channel.writeAndFlush(ackBindMessage).addListener(future -> {
                        if(future.isSuccess()){
                            logger.info("Thread-AckBind "+ackBindMessage.toString());
                        }
                    });
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
