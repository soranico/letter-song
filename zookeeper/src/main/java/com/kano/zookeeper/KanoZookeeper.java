package com.kano.zookeeper;

import com.kano.zookeeper.lock.EventWatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class KanoZookeeper {

    private static String CONNECT = "127.0.0.1:2181";

    private static final int SESSION_OUT = 1000;

    public ZooKeeper getZooKeeper(){
        try {
            CountDownLatch latch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper(CONNECT, SESSION_OUT, new EventWatch().setLatch(latch));
            latch.await();
            return zooKeeper;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getData(String  data){
        return data.getBytes(StandardCharsets.UTF_8);
    }

    public void readData(byte[] data){
        System.out.println("read data = "+new String(data));
    }

    @Test
    public void testCreateForever() throws Exception{
        ZooKeeper zooKeeper = getZooKeeper();
        zooKeeper.create("/kano-forever",getData("forever"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        byte[] data = zooKeeper.getData("/kano-forever", new EventWatch(), null);

        readData(data);

    }







}
