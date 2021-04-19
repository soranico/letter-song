package com.soranico.thread.falsesharing;

/**
 * <pre>
 * @title com.soranico.thread.falsesharing.FalseSharingData
 * @description
 *        <pre>
 *          伪共享数据
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/7/4
 *
 * </pre>
 */
public final class FalseSharingData {
    public volatile long value = 0L;
    /**
     * 填充字节保证数据不再一个cache line
     * 防止伪共享
     */
    public long p0, p1, p2, p3, p4, p5;
}
