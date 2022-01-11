package com.kanozz.io;

import sun.misc.Cleaner;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Mmp {
    public static void main(String[] args) {

        try (RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/kano/Desktop/program/kano.txt", "rw");
             FileChannel channel = randomAccessFile.getChannel()) {
            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
            Method[] methods = mappedByteBuffer.getClass().getMethods();
            Method methodName = null;
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().equals("attachment")) {
                    methodName = methods[i];
                    break;
                }
            }
            MappedByteBuffer byteBuffer = mappedByteBuffer;
            methodName.setAccessible(true);
            MappedByteBuffer attach = (MappedByteBuffer) methodName.invoke(mappedByteBuffer);

            if (attach!=null){
                byteBuffer = attach;
            }
            for (int i = 0; i < byteBuffer.getClass().getMethods().length; i++) {
                if (methods[i].getName().equals("cleaner")) {
                    methodName = methods[i];
                    break;
                }
            }

            methodName.setAccessible(true);
            Cleaner cleaner = (Cleaner) methodName.invoke(byteBuffer);
            cleaner.clean();




        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
