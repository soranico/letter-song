package com.kanonsd.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class KanoCompleteableFuture {

    public static void main(String[] args) {

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("hello world f1");
            sleep(1); // TimeUnit.SECONDS.sleep(1)
            return "result f1";
        });
        CompletableFuture<String> f2 = f1.thenApply(r -> {
            System.out.println(r);
            sleep(1);
            return "f2";
        });
        CompletableFuture<String> f3 = f2.thenApply(r -> {
            System.out.println(r);
            sleep(1);
            return "f2";
        });

        CompletableFuture<String> f4 = f1.thenApply(r -> {
            System.out.println(r);
            sleep(1);
            return "f2";
        });
        CompletableFuture<String> f5 = f4.thenApply(r -> {
            System.out.println(r);
            sleep(1);
            return "f2";
        });
        CompletableFuture<String> f6 = f5.thenApply(r -> {
            System.out.println(r);
            sleep(1);
            return "f2";
        });
    }

    private static void sleep(long time){
        try {
            TimeUnit.SECONDS.sleep(time);
        }catch (Exception e){

        }
    }



}
