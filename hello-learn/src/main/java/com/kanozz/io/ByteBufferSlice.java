package com.kanozz.io;

import java.nio.ByteBuffer;

public class ByteBufferSlice {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3);
        byteBuffer.put((byte) 4);
//        printBuffer("old ",byteBuffer);
//        byteBuffer.position(2);
//        printBuffer("old ",byteBuffer);
        ByteBuffer byteBufferNew = byteBuffer.slice();

//        printBuffer("new ",byteBufferNew);

        byteBuffer.put((byte) 5);
        byteBuffer.put((byte) 6);
        printBuffer("old update ",byteBuffer);

        printBuffer("new update ",byteBufferNew);

    }
    private static void printBuffer(String prefix, ByteBuffer buff) {
        System.out.println();
        System.out.println(prefix + buff);
        System.out.print(prefix);
        for (int i = buff.position(); i < buff.limit(); i++) {
            System.out.print(" " + buff.get(i));
        }
        System.out.println();
    }

}
