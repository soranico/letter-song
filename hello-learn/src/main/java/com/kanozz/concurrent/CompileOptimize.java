package com.kanozz.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * -XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation -XX:+PrintInlining
 * 输出内联方法信息
 */
@Slf4j
public class CompileOptimize {

    private static boolean stop = false;

    /**
     * 972  537 %     4       com.kanonsd.concurrent.CompileOptimize::lambda$innerMethod$0 @ 12 (43 bytes)
     *                        @ 19   com.kanonsd.concurrent.CompileOptimize::canInner (21 bytes)   inline (hot)
     * 如果去掉 TimeUnit.SECONDS.sleep(1);在程序会直接退出,此时还木有触发JIT
     * 推测是因为getstatic需要从堆上取数据,JIT优化为从栈上取局部变量来执行
     */
    @Test
    public void innerMethod() throws InterruptedException {

        Thread t = new Thread(() -> {
            int loop = 0;
            log.info("loop start");
            while (!stop) {
                canInner();
                loop++;
            }
            log.info("loop end = {}", loop);
        });
        t.start();
        TimeUnit.SECONDS.sleep(1);
        stop = true;
        t.join();
    }

    private void canInner() {
        int sum =0;
        for (int i = 0; i < 100; i++) {
            sum+=i;
        }
    }


    /**
     * 没有发生方法内联
     */
    @Test
    public void noInnerMethod() throws InterruptedException {

        Thread t = new Thread(() -> {
            int loop = 0;
            log.info("loop start");
            while (!stop) {
                cannotInner();
                loop++;
            }
            log.info("loop end = {}", loop);
        });
        t.start();
        TimeUnit.SECONDS.sleep(6);
        stop = true;
        t.join();
        System.out.println(0);
    }



    private void cannotInner() {

        Thread t = new Thread(()->{
            int sum =0;
            for (int i = 0; i < 100; i++) {
                sum+=i;
            }
        });
        t.start();
    }




}
