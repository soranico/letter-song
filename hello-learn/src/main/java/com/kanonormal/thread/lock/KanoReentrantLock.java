package com.kanonormal.thread.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * @title com.soranico.thread.lock.KanoReentrantLock
 * @description
 *        <pre>
 *          ReentrantLock源码学习
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/7/12
 *
 * </pre>
 */
@Slf4j
public class KanoReentrantLock {

    private static Lock lock = new ReentrantLock(false);


    public static void main(String[] args) {
        try {
            lock.lock();
            log.info("lock");
        } finally {
            lock.unlock();
            log.info("unlock");
        }
    }
}


