package com.kanonsd.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * 11:35:17.737 [main] INFO com.kanonormal.concurrent.Reorder - x = 0,y = 0,a = 1,b = 2
 * 11:35:17.745 [main] INFO com.kanonormal.concurrent.Reorder - i = 1940578
 *
 * @author kanonormal
 */
@Slf4j
public class Reorder {
    int a = 0;
    int b = 0;
    int x = -1;
    int y = -1;

    /**
     * 可能被重排序为
     * x = b
     * a =1
     */
    public void changeXAndA() {
        a = 1;
        x = b;
    }

    /**
     * 可能被重排序为
     * y = a
     * b = 2
     */
    public void changeYAndB() {
        b = 2;
        y = a;
    }

    private void wait(CyclicBarrier cy) {
        try {
            cy.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程并行执行,在cpu指令乱序情况下
     * core1执行x=b core2执行y=a同时执行 此时 a=0 b=0
     * 然后 core1执行a=1 core2执行b=2
     */
    public boolean changeNum() throws InterruptedException {
        a = b = 0;
        x = y = -1;
        CyclicBarrier cy = new CyclicBarrier(2);
        /**
         * core1Run 可能在cpu的core1执行
         */
        Thread core1Run = new Thread(() -> {
            wait(cy);
            changeXAndA();
        });
        /**
         * core2Run 可能在cpu的core2执行
         */
        Thread core2Run = new Thread(() -> {
            wait(cy);
            changeYAndB();
        });

        core1Run.start();
        core2Run.start();

        core1Run.join();
        core2Run.join();

        if (x == 0 && y == 0) {
            log.info("x = {},y = {},a = {},b = {}", x, y, a, b);
            return false;
        }
        return true;

    }

    public static void main(String[] args) throws InterruptedException {
        int loop = 10000000;
        for (int i = 0; i <= loop; i++) {
            Reorder reorder = new Reorder();
            boolean b = reorder.changeNum();
            if (!b) {
                log.info("i = {}", i);
            }
        }
    }
}
