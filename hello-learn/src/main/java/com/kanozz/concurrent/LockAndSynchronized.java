package com.kanozz.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 *
 * "sync-thread-2" #11 prio=5 os_prio=31 tid=0x00007fdd96186000 nid=0x3e03 waiting for monitor entry [0x0000700009ca8000]
 *    java.lang.Thread.State: BLOCKED (on object monitor)
 *
 * "lock-thread-2" #12 prio=5 os_prio=31 tid=0x00007fdd96186800 nid=0x5603 waiting on condition [0x0000700009dab000]
 *    java.lang.Thread.State: WAITING (parking)
 */
public class LockAndSynchronized {

    private static final Object mutex = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread syn01 = new Thread(()->{
            synchronized (mutex){
                try {
                    TimeUnit.SECONDS.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"sync-thread-1");
        syn01.start();

        Thread syn02 = new Thread(()->{
            synchronized (mutex){
                try {
                    TimeUnit.SECONDS.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"sync-thread-2");
        syn02.start();


        Thread lock = new Thread(LockSupport::park,"lock-thread-2");
        lock.start();

        syn01.join();
        syn02.join();
        lock.join();



    }

}
