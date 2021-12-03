package com.kanozz.map;

import org.junit.Test;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentMapTest {
    @Test
    public void testConcurrentSkipListMap(){
        // 跳跃表
        ConcurrentSkipListMap<String,Integer> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.put("kano",1);

    }


    @Test
    public void testConcurrentList(){
        // 方法加锁
        List<Integer> vectorList = new Vector<>();
        // 写时复制
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

//        Collections.synchronizedList();

    }

}
