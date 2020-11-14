package com.servicematrix.matrixmq.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.servicematrix.matrixmq.msg.broker.AckBindMessage;
import com.servicematrix.matrixmq.msg.client.BindMessage;
import com.servicematrix.matrixmq.msg.client.BroadCastRequest;
import com.servicematrix.matrixmq.msg.client.SingleRequest;
import com.servicematrix.matrixmq.msg.client.UnBindMessage;
import org.objenesis.strategy.StdInstantiatorStrategy;

public class KryoPoolFactory {

    private static KryoPoolFactory poolFactory = new KryoPoolFactory();

    private KryoFactory factory = () -> {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(SingleRequest.class);
        kryo.register(AckBindMessage.class);
        kryo.register(BroadCastRequest.class);
        kryo.register(BindMessage.class);
        kryo.register(UnBindMessage.class);
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        return kryo;
    };

    private KryoPool pool = new KryoPool.Builder(factory).build();

    private KryoPoolFactory() {
    }

    public static KryoPool getKryoPoolInstance() {
        return poolFactory.getPool();
    }

    public KryoPool getPool() {
        return pool;
    }
}