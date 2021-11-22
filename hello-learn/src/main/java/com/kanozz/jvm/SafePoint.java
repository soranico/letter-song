package com.kanozz.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -XX:+PrintSafepointStatistics
 * -XX:PrintSafepointStatisticsCount=1
 * -XX:+UnlockDiagnosticVMOptions
 * -XX:-DisplayVMOutput
 * -XX:+LogVMOutput
 */
public class SafePoint {

    private static final List<Object> GC = new ArrayList<>();

    public static void main(String[] args) {
        gc(1);
    }

    public static void gc(int loop) {
        if (loop > 4) {
            return;
        }
        try {
            while (true) {
                GC.add(new Object());
            }
        } catch (OutOfMemoryError e) {
            GC.clear();
            // loop++会导致死循环
            gc(++loop);
        }
    }
}
