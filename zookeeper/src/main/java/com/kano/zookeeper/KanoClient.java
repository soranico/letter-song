package com.kano.zookeeper;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class KanoClient {

    private static String CONNECT = "127.0.0.1:2181";




    public static void main(String[] args) {

        CuratorZookeeperClient client = new CuratorZookeeperClient(CONNECT, 1000, 1000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

            }
        }, new RetryOneTime(1000));



    }


}
