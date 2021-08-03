package com.kanonsd.future;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SimpleFutureTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String > task = new FutureTask<>(()->{
            TimeUnit.SECONDS.sleep(300);
            return "kano";
        });
        new Thread(task).start();

        new Thread(()->{
            try {
                task.get(10,TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"load-1").start();
        new Thread(()->{
            try {
                task.get(150,TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"load-2").start();
        new Thread(()->{
            try {
                task.get(160,TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"load-3").start();


        String kano = task.get();
        log.info("{}",kano);
    }

    @Test
    public void testCancel() throws InterruptedException {
        FutureTask<String > task = new FutureTask<>(()->{
            TimeUnit.SECONDS.sleep(180);
            return "kano";
        });

        Thread taskThread = new Thread(task, "task-thread");

        Thread getThread = new Thread(() -> {
            Field state = null;
            try {
                state = FutureTask.class.getDeclaredField("state");
                state.setAccessible(true);
                task.get(2,TimeUnit.SECONDS);
            }catch (Exception e){

            }

            while (true){
                try {
                    int cur = (int)state.get(task);
                    if (cur == 5){
                        System.err.println(cur);
                        break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "get-thread");


        taskThread.start();
        getThread.start();
        TimeUnit.SECONDS.sleep(1);

        Thread cancelThread = new Thread(() -> {
            try {
                task.cancel(true);
                log.info("cancel");
            } catch (Exception e) {
               e.printStackTrace();
            }
        }, "cancel-thread");

        cancelThread.start();

        taskThread.join();



    }



}
