package com.kanonsd.jvm;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Markword {


    @SneakyThrows
    @Test
    public void nonLock() {

        TimeUnit.SECONDS.sleep(60);
        Object mutex = new Object();
        log.info("before = {}", ClassLayout.parseInstance(mutex).toPrintable());
        synchronized (mutex) {
            TimeUnit.MILLISECONDS.sleep(10);
        }
        log.info("after = {}", ClassLayout.parseInstance(mutex).toPrintable());

    }


}
