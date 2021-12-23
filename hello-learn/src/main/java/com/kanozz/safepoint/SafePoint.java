package com.kanozz.safepoint;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * -XX:+PrintGCApplicationStoppedTime
 *
 *
 * -XX:+PrintSafepointStatistics
 *
 * –XX:PrintSafepointStatisticsCount=1
 */
public class SafePoint {

    private static final List<Object> cache = new ArrayList<>();
    public static void main(String[] args) {
        try {
            while (true){
                cache.add(new Object());
            }
        }catch (OutOfMemoryError e){
            cache.clear();

        }

    }
}
