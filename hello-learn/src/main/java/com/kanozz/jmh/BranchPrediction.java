package com.kanozz.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * c.k.j.BranchPrediction.nonSort    avgt        3  24298782804.800 ± 68008582404.435  ns/op
 * c.k.j.BranchPrediction.sort       avgt        3   3658400961.800 ±  1792712645.436  ns/op
 *
 * 取指（Fetch）：取指阶段从存储器读取指令字节，地址为程序计数器（PC）的值。
 * 译码（Decode）：译码阶段完成指令的翻译，从寄存器文件读入最多两个操作数。
 * 执行（Execute）：执行指令，如果是执行的是一条跳转指令的话，这个阶段会检查条件码和分支条件，决定是否选择分支。
 * 写回（Write Back）：将指令执行结果保存到内存中。
 *
 */
@BenchmarkMode(Mode.AverageTime)
/** 测试完成时间  平均时间模式 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
/** 时间单位 ns */
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
/** 预热 2 轮，每次 1s */
@Measurement(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
/** 测试 5 轮，每次 3s */
@Fork(1)
/**  fork 1 个线程 */
@Threads(5)
/** 5个线程 */
@State(Scope.Thread)
/** 每个测试线程一个实例 */
public class BranchPrediction {


    int arraySize = 32768;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BranchPrediction.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }


    @Benchmark
    public void nonSort(Blackhole blackhole) {
        int[] data = new int[arraySize];
        //生成0-255范围的随机数并存入数组data
        Random random = new Random();
        for (int c = 0; c < arraySize; ++c) {
            data[c] = random.nextInt(arraySize) & 255;
        }

        long sum = 0;
        for (int i = 0; i < 100000; ++i) {
            for (int c = 0; c < arraySize; ++c) {
                /**
                 * cpu分支预测情况下,可能出现预测失败
                 * CPU将预测到的指令直接放入流水线，去执行指令的取指、译码
                 * 比如当前预测 data[c] >= 128成立
                 * 取下一条指令的取指,译码成为 a
                 * 此时分支跳转指令执行完成,判断不需要跳转
                 * 则需要将 a的指令操作清空,跳转到指定位置再次
                 * 取指,译码
                 * 导致流水线变慢
                 *
                 */
                if (data[c] >= 128) {
                    sum += data[c];
                }
            }
        }
        blackhole.consume(sum);
    }

    @Benchmark
    public void sort(Blackhole blackhole) {
        int[] data = new int[arraySize];
        //生成0-255范围的随机数并存入数组data
        Random random = new Random();
        for (int c = 0; c < arraySize; ++c) {
            data[c] = random.nextInt(arraySize) & 255;
        }
        //对数组进行排序
        Arrays.sort(data);
        long sum = 0;
        for (int i = 0; i < 100000; ++i) {
            for (int c = 0; c < arraySize; ++c) {
                /**
                 *
                 * 排序后因为有序,所以预测成功变大
                 * 从而流水线执行变快,可以再分支跳转指令
                 * 执行时就完成下一条指令的取指,译码
                 * 并且分支跳转指令完成的结果和预测相同
                 * 不需要清空之前预测的指令操作结果
                 *
                 */
                if (data[c] >= 128) {
                    sum += data[c];
                }
            }
        }
        blackhole.consume(sum);
    }


    @Benchmark
    public void nonSortWithBit(Blackhole blackhole) {
        int[] data = new int[arraySize];
        //生成0-255范围的随机数并存入数组data
        Random random = new Random();
        for (int c = 0; c < arraySize; ++c) {
            data[c] = random.nextInt(arraySize) & 255;
        }
        //对数组进行排序
        long sum = 0;
        for (int i = 0; i < 100000; ++i) {
            for (int c = 0; c < arraySize; ++c) {

                // 取出符号位
                int bit = (data[c]-128)>>31;
                // ~0&num = num ~-1&num = 0
                sum += ~bit&data[c];
            }
        }
        blackhole.consume(sum);
    }

}