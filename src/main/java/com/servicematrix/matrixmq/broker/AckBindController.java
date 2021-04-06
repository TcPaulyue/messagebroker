package com.servicematrix.matrixmq.broker;


import com.servicematrix.matrixmq.broker.applicationContext.ApplicationContextCluster;
import com.servicematrix.matrixmq.broker.clientCluster.RemoteClientCluster;
import com.servicematrix.matrixmq.msg.broker.AckBindMessage;
import com.servicematrix.matrixmq.msg.client.BindMessage;
import io.netty.channel.Channel;


import static com.servicematrix.matrixmq.broker.MQBroker.BROKERID;

public class AckBindController implements Runnable {
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
           // System.out.println(bindMessage);
            if(bindMessage!=null){
                Channel channel = RemoteClientCluster.getChannel(bindMessage.getChannelId());
                if(channel!=null){
                    AckBindMessage ackBindMessage = new AckBindMessage();
                    ackBindMessage.setBrokerId(BROKERID);
                    ackBindMessage.setTime(System.currentTimeMillis());
                    channel.writeAndFlush(ackBindMessage);
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
