package com.kanonsd.future;

import java.util.concurrent.CountDownLatch;

public class KanoFinal {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        modify(latch);

    }

    private static void modify(CountDownLatch latch ){
        latch = new CountDownLatch(2);
    }
}
