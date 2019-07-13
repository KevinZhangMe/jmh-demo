package com.technologiesinsight.jmh;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 字符串拼接基准测试
 *
 * 测试结果：
 * <pre>
 * Benchmark                                         Mode  Cnt   Score    Error  Units
 * StringAppendBenchmarkTenK.stringAddBenchmark      avgt   25  82.590 ± 14.824  ms/op
 * StringAppendBenchmarkTenK.stringBufferBenchmark   avgt   25   0.127 ±  0.005  ms/op
 * StringAppendBenchmarkTenK.stringBuilderBenchmark  avgt   25   0.146 ±  0.010  ms/op
 * </pre>
 *
 * @author KevinZhang <kevin.zhang.me@gmail.com>
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StringAppendBenchmarkTenK {

    public static final int TEN_K = 10000;

    @Benchmark
    public String stringAddBenchmark() {
        String targetString = "";
        for (int i = 0; i < TEN_K; i++) {
            targetString += "hello";
        }
        return targetString;
    }

    @Benchmark
    public String stringBuilderBenchmark() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TEN_K; i++) {
            sb.append("hello");
        }
        return sb.toString();
    }

    @Benchmark
    public String stringBufferBenchmark() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < TEN_K; i++) {
            sb.append("hello");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(StringAppendBenchmarkTenK.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
