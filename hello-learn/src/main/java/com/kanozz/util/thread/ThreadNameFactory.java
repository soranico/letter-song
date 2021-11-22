package com.kanozz.util.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * @title com.soranico.util.thread.ThreadNameFactory
 * @description
 *        <pre>
 *          线程工厂
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/7/4
 *
 * </pre>
 */
public class ThreadNameFactory implements ThreadFactory {

    /**
     * 全局线程池名
     */
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    /**
     * 每个线程池的名字
     */
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String threadPrefix;

    private final ThreadGroup group;

    private Integer priority;

    private Boolean daemon;

    public ThreadNameFactory(String threadPoolName) {
        SecurityManager securityManager = System.getSecurityManager();
        this.group = securityManager == null ?
                Thread.currentThread().getThreadGroup() : securityManager.getThreadGroup();
        this.threadPrefix = threadPoolName + "-" + poolNumber.getAndIncrement() + "-";
    }

    public ThreadNameFactory(String threadPoolName, Integer priority) {
        this(threadPoolName);
        this.priority = priority;
    }

    public ThreadNameFactory(String threadPoolName, Integer priority, Boolean daemon) {
        this(threadPoolName, priority);
        this.daemon = daemon;
    }

    /**
     * 创建线程池
     * @param r
     * @return
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group,r,threadPrefix + threadNumber.getAndIncrement());
        if (priority!=null){
            thread.setPriority(priority);
        }
        if (daemon!=null){
            thread.setDaemon(daemon);
        }
        return thread;
    }
}
