package com.kanozz.io;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ThreadMmpListen {
    public static void main(String[] args) throws Exception {

        CountDownLatch latch = new CountDownLatch(1);

        Thread t1 = new Thread(() -> {
            try (RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/kano/Desktop/program/kano.txt", "rw");
                 FileChannel channel = randomAccessFile.getChannel()) {

                MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
                latch.await();
                mappedByteBuffer.putLong(1L);
                mappedByteBuffer.putLong(2L);

                mappedByteBuffer.force();
                Thread.sleep(10000);
                mappedByteBuffer.force();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        Thread t2 = new Thread(() -> {
            try (RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/kano/Desktop/program/kano.txt", "rw");
                 FileChannel channel = randomAccessFile.getChannel()) {

                MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);

                long read = mappedByteBuffer.getLong();
                System.out.println("before force read = "+read);
                latch.countDown();
                read = mappedByteBuffer.getLong();
                System.out.println("after force read = "+read);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t2.start();

        t1.join();
        t2.join();


    }


}
