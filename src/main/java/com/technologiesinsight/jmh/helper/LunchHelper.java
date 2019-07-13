package com.technologiesinsight.jmh.helper;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author KevinZhang <kevin.zhang.me@gmail.com>
 */
public final class LunchHelper {
    public static void lunchBenchmark(Class clazz) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(clazz.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
