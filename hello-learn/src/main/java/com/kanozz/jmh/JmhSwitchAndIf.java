package com.kanozz.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * @title com.soranico.jmh.JmhSwitchAndIf
 * @description
 *        <pre>
 *          switch和if性能比较
 *          基于JMH
 *        </pre>
 *        <pre>
 *            JmhSwitchAndIf.ifTest      avgt    5  14.168 ± 1.178  ns/op
 *            JmhSwitchAndIf.switchTest  avgt    5  12.927 ± 0.475  ns/op
 *            switch 比 if 性能稍好
 *
 *            javap反编译可以看出 getstatic 取出来 kano的值
 *              switch的时候 只取出来一次字符串然后比较hashcode
 *            在hashcode相同的时候再去比较equals
 *              if的时候每次进入if() else if()都会取出来一次字符串
 *              然后调用equals
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/7/5
 *
 * </pre>
 */
@BenchmarkMode(Mode.AverageTime)
/** 测试完成时间  平均时间模式 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
/** 时间单位 ns */
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
/** 预热 2 轮，每次 1s */
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
/** 测试 5 轮，每次 3s */
@Fork(1)
/**  fork 1 个线程 */
@Threads(5)
/** 5个线程 */
@State(Scope.Thread)
/** 每个测试线程一个实例 */
public class JmhSwitchAndIf {

    private static String kano = "kano";

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JmhSwitchAndIf.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }


    @Benchmark
    public void switchTest(Blackhole blackhole) {
        String result;
        switch (kano) {
            case "letter":
                result = "letter";
                break;
            case "song":
                result = "song";
                break;
            case "hello":
                result = "hello";
                break;
            case "how":
                result = "how";
                break;
            case "are":
                result = "are";
                break;
            case "you":
                result = "you";
                break;
            default:
                result = "kano";
                break;
        }
        blackhole.consume(result);
    }

    @Benchmark
    public void ifTest(Blackhole blackhole) {
        String result;
        if ("letter".equals(kano)) {
            result = "letter";
        } else if ("song".equals(kano)) {
            result = "song";
        } else if ("hello".equals(kano)) {
            result = "hello";
        } else if ("how".equals(kano)) {
            result = "how";
        } else if ("are".equals(kano)) {
            result = "are";
        } else if ("you".equals(kano)) {
            result = "you";
        } else {
            result = "kano";
        }
        // 为了避免 JIT 忽略未被使用的结果计算，可以使用 Blackhole#consume 来保证方法被正常执行
        blackhole.consume(result);
    }
}