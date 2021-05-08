package com.kanonormal.thread.lock;

import com.kanonormal.util.thread.ThreadNameFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * @title com.soranico.thread.lock.KanoWaitNotify
 * @description
 *        <pre>
 *          wait和notify为什么需要synchronized
 *
 *          1.只有重量级锁才会被Monitor监视，同时才会可能进入_WaitSet
 *          假设没有synchronized,当调用wait()的时候，threadA需要释放锁
 *          并且进入_WaitSet,由于没有被synchronized标记,就没有_WaitSet
 *          那么怎么进入，所以会抛出异常
 *          同理，notify() | notifyAll()会唤醒_WaitSet，如果没有怎么唤醒
 *
 *
 *
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/7/12
 *
 * </pre>
 */
@Slf4j
public class KanoWaitNotify {

    /**
     * java object header
     *
     * mark word 32bit OS 64bit OS类似
     * 哈希码(hash) 年龄(age) 偏向锁(biased_lock) 锁标记(lock) 偏向线程ID(JavaThread*) 偏向时间戳(epoch)
     * 指向锁记录的指针，指向栈中锁记录的指针(ptr_to_lock_record)
     * 指向重量级锁的指针，指向对象监视器Monitor(ptr_to_heavyweight_monitor) 里面维护了_EntrySet _WaitSet
     * 1.无锁
     *   25bit  4bit       1bit         2bit
     *  hash    age    biased_lock(0)  lock(01)
     * 2.偏向锁
     *   23bit         2bit    4bit    1bit          2bit
     *  JavaThread*  epoch    age  biased_lock(1)  lock(01)
     *3.轻量级锁
     *    30bit               2bit
     *  ptr_to_lock_record    lock(00)
     *4.重量级锁
     *   30bit                       2bit
     * ptr_to_heavyweight_monitor    lock(10)
     * 5.GC
     *  30bit   2bit
     *         lock(11)
     */
    private final static Object monitor = new Object();
    private static ThreadPoolExecutor fixedPool = new ThreadPoolExecutor(4, 4, 0,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadNameFactory("wait-notify-pool"));

    public static void main(String[] args) {
        fixedPool.execute(new KanoWaitThread());
        fixedPool.execute(new KanoNotifyThread());
        fixedPool.shutdown();

    }
    private static class KanoWaitThread implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            synchronized (monitor) {
                log.info("kanoWaitThread start");
                /**
                 * 进入到WAIT_ENTRY
                 */
                monitor.wait();
                log.info("kanoWaitThread end");
            }
        }
    }
    private static class KanoNotifyThread implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            synchronized (monitor) {
                log.info("kanoNotifyThread start");
                /**
                 * 唤醒WAIT_ENTRY的线程
                 * 如果没有同步块
                 */
                monitor.notify();
                log.info("kanoNotifyThread end");
            }
        }
    }


}
