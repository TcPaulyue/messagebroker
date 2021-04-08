package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.netty.RequestDataDecoder;
import com.servicematrix.matrixmq.netty.ResponseDataEncoder;
import com.servicematrix.matrixmq.serialize.KryoCodecUtil;
import com.servicematrix.matrixmq.serialize.KryoPoolFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;


import static com.servicematrix.matrixmq.broker.MessageBrokerHandler.executorService;

public class MQBroker {
    public static final String BROKERID = "MATRIXMQ_01";

    private static final Logger logger = Logger.getLogger(MQBroker.class);

    private final int port;

    private static final KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());

    private boolean isRunning = false;

    public MQBroker(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new RequestDataDecoder(util),
                                    new ResponseDataEncoder(util),
                                    new MessageBrokerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = null;
            try {
                f = b.bind(port).sync();
                f.addListener((ChannelFutureListener) channelFuture -> {
                    if(channelFuture.isDone()){
                        logger.info("broker-"+BROKERID+" bind port:"+port);
                    }
                    if(channelFuture.isSuccess()){
                        logger.info("broker-"+BROKERID+" bind port:"+port+" succeed.");
                    }else{
                        logger.info("broker-"+BROKERID+" bind port:"+port+" failed.");
                    }
                });
                isRunning = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.execute(new AckBindController());
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            isRunning = false;
        }
    }


    public static void main(String[] args) {
//        List<BrokerStrategy> brokerStrategyList = new ArrayList<>();
//
//        List<RemoteClientInfo> clientInfoList = new ArrayList<>();
//        BrokerStrategyA brokerStrategyA = new BrokerStrategyA(clientInfoList);
//        //brokerStrategyList.add(brokerStrategyA);
//
//        List<String> stringList = new ArrayList<>();
//        stringList.add("demo_1");
//        BrokerStrategyB brokerStrategyB = new BrokerStrategyB(stringList);
//        brokerStrategyList.add(brokerStrategyB);
        MQBroker mqBroker = new MQBroker(8080);
        mqBroker.run();



    }

    public boolean isRunning() {
        return isRunning;
    }
}
