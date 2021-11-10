package com.kanonsd.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KanoGenericType {

    public static void main(String[] args) {
        test();
    }


    public static void test(){
        /**
         * List 没有添加的赋值的限制
         * noneList.add(new Music());
         * noneList = anyList;
         */
        List noneList = new ArrayList();
        noneList.add(new Music());
        noneList.add(new Comic());
        noneList.add(new Kano());
        noneList.add(new Hanser());


        List<Object> anyList = new ArrayList<>();
        noneList = anyList;
        anyList.add(new Music());
        anyList.add(new Comic());
        anyList.add(new Kano());
        anyList.add(new Hanser());

        List<Music> musicList = new ArrayList<>();
        musicList.add(new Music());
        musicList.add(new Comic());
        musicList.add(new Kano());

//        musicList.add(new Hanser());
        List<?> genericList = new ArrayList<>();
        genericList = musicList;

        Map<?,Object> map = new HashMap<>();
//        map.put(1,new Object());

        Map<Object,?> map1 = new HashMap<>();
//        map1.put(1,new Object());

        List<? extends Music> extendList = new ArrayList<>();
        extendList = musicList;


        Music music = extendList.get(0);

        List<? super Music> superList = new ArrayList<>();
        superList.add(new Music());
        Object object = superList.get(0);


    }

    static class Music { }
    static class Comic extends Music{}
    static class Kano extends Comic{}
    static class Hanser{}

}
