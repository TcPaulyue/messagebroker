package com.servicematrix.client;



import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Client extends NettyClient {

    protected static List<String> topics = Arrays.asList("machine1", "machine2", "machine3");

    protected static List<String> messages = Arrays.asList("Donald", "Theresa", "Vladimir", "Angela", "Emmanuel", "Shinzō", "Jacinda", "Kim");

    @Override
    protected void sendMessage(String message, String topic) throws InterruptedException {
        RequestData msg = new RequestData(id);
        msg.setCount(new Random().nextInt(5));
        msg.setTopic(topic);
        msg.setMessage(message);
        channelFuture.channel().writeAndFlush(msg);
       // channelFuture.channel().closeFuture().sync();
    }

    public void init(String host, Integer port) throws Exception {
        super.init(host,port);
    }


    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.init("localhost",8080);
        client.sendMessage(topics.get(new Random().nextInt(topics.size())),messages.get(new Random().nextInt(messages.size())));
        client.sendMessage(topics.get(new Random().nextInt(topics.size())),messages.get(new Random().nextInt(messages.size())));
        client.sendMessage(topics.get(new Random().nextInt(topics.size())),messages.get(new Random().nextInt(messages.size())));
        client.closeChannel();

    }
}
