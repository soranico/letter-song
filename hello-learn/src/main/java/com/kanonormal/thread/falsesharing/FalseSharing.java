package com.kanonormal.thread.falsesharing;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * @title com.soranico.thread.falsesharing.FalseSharing
 * @description
 *        <pre>
 *          伪共享
 *
 *          现有数据Data 它的实例data大小不足一个cache line
 *          有线程threadA threadB去操作一个连续data实例的结构
 *          当threadA所在cpu coreA加载dataA时 由于dataA的size
 *          不足cache line 则会加载下一个数据 dataB
 *          同理threadB 的cpu coreB加载dataB dataA
 *
 *          当threadA修改dataA的时候由于MESI缓存一致性协议 则需要将
 *          threadA加载的那段内存置为I(Invalid)，内存中dataA dataB无效
 *          加载dataA dataB的cpu缓存数据无效
 *          当threadB去修改dataB的时候发现dataB无效，需要重内存中重新加载
 *
 *          在高并发情况下，会导致cache失效，效率下降
 *
 *          伪共享存在耗时   561.2 ms
 *          伪共享消除耗时  1967.3 ms
 *          伪共享jdk8耗时  558.2 ms (-XX:-RestrictContended 启用 @Contended)
 *
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/7/4
 *
 * </pre>
 */
@Slf4j
public class FalseSharing implements Runnable {

    /**
     * 线程数
     */
    private static final int THREAD_NUMBER = 4;
    /**
     * 每个线程的循环次数
     */
    private static final long PER_LOOP_NUMBER = 1000000L << 6;
    /**
     * 数组下标
     */
    private final int dataIndex;
    /**
     * 存放数据
     * 由于cpu加载数据按照cache line加载
     * 所以数组中的data0不足一个cache line
     * 则会加载data0 data1 直达满足cache line
     */
    private static FalseSharingData[] falseSharingData = new FalseSharingData[THREAD_NUMBER];

    private static int loopTime = 10;

    private static long sumTime = 0;

    public FalseSharing(final int dataIndex) {
        this.dataIndex = dataIndex;
    }

    /**
     * 启动
     */
    private static void start() throws InterruptedException {

        Thread[] threads = new Thread[THREAD_NUMBER];
        for (int i = 0; i < THREAD_NUMBER; i++) {
            threads[i] = new Thread(new FalseSharing(i), "thread-" + (i + 1));
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

    }

    /**
     * 线程赋值
     */
    @Override
    public void run() {
        long loopNumber = PER_LOOP_NUMBER;
        while ((loopNumber--) != 0) {
            falseSharingData[dataIndex].value = loopNumber;
        }
    }


    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < loopTime; i++) {
            for (int j = 0; j < THREAD_NUMBER; j++) {
                falseSharingData[j] = new FalseSharingData();
            }
            long startTime = System.currentTimeMillis();
            start();
            long endTime = System.currentTimeMillis();
            log.info("第{}轮耗时 : {} ms", i + 1, endTime - startTime);
            sumTime += (endTime - startTime);
        }
        log.info("平均耗时 = {} ms", (sumTime / 10.0));
    }


}
