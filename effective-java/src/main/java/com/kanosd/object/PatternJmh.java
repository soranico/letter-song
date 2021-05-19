package com.kanosd.object;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * c.k.o.PatternJmh.patternStr    avgt        5   5842610917.680 ±  1544041741.902  ns/op
 * c.k.o.PatternJmh.strPattern    avgt        5  10145545267.280 ± 21658593497.318  ns/op
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(5)
@State(Scope.Thread)
public class PatternJmh {


    public static final String patternStr = "^(?=.)M*(C[MD]D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$";

    public static final Pattern pattern = Pattern.compile(patternStr);

    public static final int loop = 1000000;

    @Benchmark
    public void strPattern(Blackhole blackhole){
        long sum =0;
        for (int i = 0; i < loop; i++) {
            String s = UUID.randomUUID().toString();
            if (s.matches(patternStr)) {
                sum++;
            }
        }
        blackhole.consume(sum);
    }

    @Benchmark
    public void patternStr(Blackhole blackhole){
        long sum =0;
        for (int i = 0; i < loop; i++) {
            String s = UUID.randomUUID().toString();
            if (pattern.matcher(s).matches()) {
                sum++;
            }
        }
        blackhole.consume(sum);
    }

    public static void main(String[] args) throws RunnerException {
        Options os = new OptionsBuilder().
                include(PatternJmh.class.getSimpleName()).build();
        new Runner(os).run();
    }


}
