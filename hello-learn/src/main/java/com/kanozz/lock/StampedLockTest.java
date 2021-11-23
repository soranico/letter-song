package com.kanozz.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

@Slf4j
public class StampedLockTest {

    public static void main(String[] args) throws Exception {
        StampedLock stampedLock = new StampedLock();
        long stamp = stampedLock.tryOptimisticRead();

        log.info("same = {}", stampedLock.validate(stamp));
        stamp = stampedLock.tryOptimisticRead();
        new Thread(() -> {
            try {
                stampedLock.asWriteLock().lock();
                stampedLock.asWriteLock().lock();
            } finally {
                stampedLock.asWriteLock().unlock();
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        log.info("same = {}", stampedLock.validate(stamp));

//        stamp = stampedLock.tryOptimisticRead();
//        new Thread(() -> {
//            try {
//                stampedLock.asReadLock().lock();
//            } finally {
//                stampedLock.asReadLock().unlock();
//            }
//        }).start();
//
//        log.info("same = {}", stampedLock.validate(stamp));



    }
}
