package com.servicematrix.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.servicematrix.msg.*;
import org.objenesis.strategy.StdInstantiatorStrategy;

public class KryoPoolFactory {

    private static KryoPoolFactory poolFactory = new KryoPoolFactory();

    private KryoFactory factory = () -> {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(LoginClientMessage.class);
        kryo.register(NormalClientMessage.class);
        kryo.register(LogoutClientMessage.class);
        kryo.register(ReplyMessage.class);
        kryo.register(RoutingMessage.class);
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