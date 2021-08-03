package com.kanonsd.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ConcurrentWhenArrayList {

    private static volatile List<String > beanNames = new ArrayList<>(11);

    private static volatile  boolean stop  = false;
    private static final Object mutex = new Object();
    static {
        init();
    }
    private static void init(){
       beanNames.add("1");
       beanNames.add("2");
       beanNames.add("3");
       beanNames.add("4");
    }


    @Test
    public void forAndAdd() throws InterruptedException {
        Thread addThread = new Thread(()->{
            synchronized (mutex){
                beanNames.add("5");
                stop = true;
            }
        },"addThread");

        Thread forThread = new Thread(()->{
            while (!stop){
                for (String beanName : beanNames) {
                    log.info(beanName);
                }
            }
        },"forThread");

        forThread.start();
        addThread.start();

        forThread.join();
        addThread.join();
    }


}
