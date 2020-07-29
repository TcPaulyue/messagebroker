package com.servicematrix.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.servicematrix.client.RequestData;
import com.servicematrix.server.ResponseData;
import org.objenesis.strategy.StdInstantiatorStrategy;

public class KryoPoolFactory {

    private static KryoPoolFactory poolFactory = new KryoPoolFactory();

    private KryoFactory factory = () -> {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(ResponseData.class);
        kryo.register(RequestData.class);
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