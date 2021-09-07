package com.kano.zookeeper.lock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

public class EventWatch implements Watcher {

    private CountDownLatch latch ;

    public EventWatch setLatch(CountDownLatch latch) {
        this.latch = latch;
        return this;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println();
        System.out.println("eventType = "+event.getType());
        System.out.println("eventPath = "+event.getPath());
        System.out.println("eventState = "+event.getState());
        System.out.println("eventWrapper = "+event.getWrapper());
        latch.countDown();
    }
}
