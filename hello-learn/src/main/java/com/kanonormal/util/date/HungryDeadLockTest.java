import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class HungryDeadLockTest {

    private static ThreadPoolExecutor executor;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        TimeUnit unit = TimeUnit.HOURS;
        BlockingQueue workQueue = new LinkedBlockingQueue();
        executor = new ThreadPoolExecutor(5, 5, 1000, unit, workQueue);

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(executor);
        }).start();

        int loop = 0;
        while (true) {
            System.out.println("loop start. loop = " + (loop));
            innerFutureAndOutFuture();
            System.out.println("loop end. loop = " + (loop++));
            Thread.sleep(10);
            break;
        }
        executor.shutdown();
    }

    public static void innerFutureAndOutFuture() throws ExecutionException, InterruptedException {
        Callable<String> innerCallable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(100);
                return "inner callable";
            }
        };

        Callable<String> outerCallable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(10);
                // 提交任务到等待队列，但此时需要等待任务
                Future<String> innerFuture = executor.submit(innerCallable);
                String innerResult = innerFuture.get();
                System.out.println("inner result = "+ innerResult);
                Thread.sleep(10);
                return "outer callable. inner result = " + innerResult;
            }
        };

        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            System.out.println("submit : " + i);
            // 提交了任务，其中有5个在等待队列
            Future<String> outerFuture = executor.submit(outerCallable);
            futures.add(outerFuture);
        }
        for (int i = 0; i < 10; i++) {
            String outerResult = futures.get(i).get();
            System.out.println(outerResult + ":" + i);
        }
    }
}