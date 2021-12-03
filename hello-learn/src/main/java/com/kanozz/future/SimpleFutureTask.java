package com.kanozz.future;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.*;

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


    @Test
    public void testCancelIsRunning() throws Exception{
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,1,0,TimeUnit.SECONDS,new ArrayBlockingQueue<>(1));
        Task task;
        CompletableFuture future1 = CompletableFuture.runAsync(task = new Task(()->{
            try {
                log.info("future1 start");
//                while (true){
//
//                }
                TimeUnit.SECONDS.sleep(10);

            }catch (Exception e){
                log.info("future1 end");
                e.printStackTrace();
            }finally {
                log.info("future1 final");
            }
        }),poolExecutor);

        CompletableFuture future2 = CompletableFuture.runAsync(new Task(()->{
            try {
                log.info("future2 start");
                TimeUnit.SECONDS.sleep(3);
                log.info("future2 end");
            }catch (Exception e){
                e.printStackTrace();
            }
        }),poolExecutor);

        try {
            future1.get(3,TimeUnit.SECONDS);
        }catch (Exception e){
            log.info("future1 cancel");
            future1.complete("");
            task.stopTask();
        }


//        CompletableFuture future3 = CompletableFuture.runAsync(()->{
//            try {
//                log.info("future3 end");
//            }catch (Exception e){
//
//            }
//        },poolExecutor);


       while (poolExecutor.getActiveCount()>0){
           TimeUnit.SECONDS.sleep(1);
       }


    }

    private static class Task implements Runnable{
        private volatile boolean run;
        private volatile boolean cancel;
        private volatile  Thread runThread;
        private Runnable task;

        public Task(Runnable task){
            this.task = task;
        }

        @Override
        public void run() {
            if (cancel){
                return;
            }
            runThread = Thread.currentThread();
            run = true;
            task.run();
        }

        public void stopTask(){
            if (run){
                runThread.interrupt();
                return;
            }
            cancel = true;
        }
    }


    @Test
    public void testFinally(){
        log.info("{}",kano());
    }

    private boolean kano(){
        int i = 0;
        try {
            return i>0;
        }finally {
            i = 2;
//            return false;
        }
    }

    private boolean kano1(){
       Object kano = null;
        try {
            return kano!=null;
        }finally {
            kano = new Object();
        }
    }


}
