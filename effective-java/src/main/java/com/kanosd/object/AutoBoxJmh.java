package com.kanosd.object;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author kanosd
 * AutoBoxJmh.autoSum  avgt    5  49576968082.960 ± 6050562864.121  ns/op
 * AutoBoxJmh.baseSum  avgt    5   2867280879.440 ± 3208841669.075  ns/op
 * 在大量计算下，使用自动拆包会造成大量的性能影响
 * 因为jvm底层的自动拆装箱是创建包装类型对象实现的
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(5)
@State(Scope.Thread)
public class AutoBoxJmh {

    @Benchmark
    public void baseSum(Blackhole blackhole){
        long sum = 0L;
        for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
            sum+=i;
        }
        blackhole.consume(sum);
    }

    @Benchmark
    public void autoSum(Blackhole blackhole){
        Long sum = 0L;
        for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
            sum+=i;
        }
        blackhole.consume(sum);
    }

    public static void main(String[] args) throws RunnerException {
        Options os = new OptionsBuilder().
                include(AutoBoxJmh.class.getSimpleName()).build();
        new Runner(os).run();
    }
}
