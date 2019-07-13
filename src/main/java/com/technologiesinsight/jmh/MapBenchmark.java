package com.technologiesinsight.jmh;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import com.technologiesinsight.jmh.helper.LunchHelper;

/**
 * 测试不同 Map 的读取性能
 *
 * @author KevinZhang <kevin.zhang.me@gmail.com>
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
//@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
//@Warmup(iterations = 0, time = 1, timeUnit = TimeUnit.SECONDS)
//@Fork(1)
@State(Scope.Benchmark)
@SuppressWarnings("unused")
public class MapBenchmark {

    /**
     * JMH 会按照顺序注入type的值，并运行
     */
    @Param({"hashMap", "treeMap", "concurrentHashMap"})
    private String type;

    private Map<Integer, Integer> map;

    private int begin;
    private int end;


    @Setup(Level.Iteration)
    public void setup() {
        switch (type) {
            case "hashMap":
                map = new HashMap<>();
                break;
            case "treeMap":
                map = new TreeMap<>();
                break;
            case "concurrentHashMap":
                map = new ConcurrentHashMap<>();
                break;
            default:
                throw new IllegalStateException("Unknown type: " + type);
        }
        begin = 0;
        end = 102400;
        for (int i = begin; i < end; i++) {
            map.put(i, i);
        }
    }

    /**
     * 测试入口
     * @param bh 黑洞，用来消费产生的结果，避免编译器将循环优化掉
     */
    @Benchmark
    public void testMapGet(Blackhole bh) {
        for (int i = begin; i < end; i++) {
            bh.consume(map.get(i));
        }
    }

    public static void main(String[] args) throws RunnerException {
        LunchHelper.lunchBenchmark(MapBenchmark.class);
    }

}
