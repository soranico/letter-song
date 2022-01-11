package com.kanozz.cinit;

/**
 *
 * 类初始化死锁问题
 *
 *
 */
import java.util.concurrent.TimeUnit;

public class KanoCinitTest {

    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(()->{
            new KanoA();
        });
        Thread t2 = new Thread(()->{
            new KanoB();
        });

        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(2);
        System.err.println("main end");

    }

}
