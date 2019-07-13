package com.technologiesinsight.jmh;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.RunnerException;

import com.technologiesinsight.jmh.helper.LunchHelper;


/**
 *  synchronized 锁 vs AtomicLong vs LongAdder 基准测试
 *
 * <pre>
 * Benchmark                                  Mode  Cnt    Score    Error  Units
 * ThreadSafeLong.testAtomicLongIncrement     avgt   25  383.194 ± 23.359  ms/op
 * ThreadSafeLong.testLongAdderIncrement      avgt   25  108.105 ±  2.911  ms/op
 * ThreadSafeLong.testPrimitiveLongIncrement  avgt   25  908.964 ± 29.782  ms/op
 * </pre>
 * @author KevinZhang <kevin.zhang.me@gmail.com>
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
//@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
//@Warmup(iterations = 0, time = 1, timeUnit = TimeUnit.SECONDS)
//@Fork(1)
@State(Scope.Benchmark)
@SuppressWarnings("unused")
public class ThreadSafeLong {
    private static final Integer LOOP = 10000000;
    private final Object lock = new Object();

    private AtomicLong atomicLong;
    private LongAdder longAdder;
    private long primitiveLong;


    @Setup(Level.Iteration)
    public void setup() {
        this.atomicLong = new AtomicLong();
        this.longAdder = new LongAdder();
        this.primitiveLong = 0L;
    }

    @Benchmark
    @Threads(2)
    public long testPrimitiveLongIncrement() {
        for (int i = 0; i < LOOP; i++) {
            synchronized (lock) {
                primitiveLong = primitiveLong + 1;
            }
        }
        return primitiveLong;
    }

    @Benchmark
    @Threads(2)
    public long testAtomicLongIncrement() {
        for (int i = 0; i < LOOP; i++) {
            atomicLong.incrementAndGet();
        }
        return atomicLong.get();
    }

    @Benchmark
    @Threads(2)
    public long testLongAdderIncrement() {
        for (int i = 0; i < LOOP; i++) {
            longAdder.increment();
        }
        return longAdder.longValue();
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        long atomicResult = atomicLong.get();
        long longAdderResult = longAdder.longValue();
        System.out.println(String.format("primitiveLongResult is %s,atomicResult is :%s;longAdderResult is %s", primitiveLong, atomicResult, longAdderResult));
    }

    public static void main(String[] args) throws RunnerException {
        LunchHelper.lunchBenchmark(ThreadSafeLong.class);
    }

}


