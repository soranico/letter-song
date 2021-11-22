package com.kanozz.generic;

import java.util.ArrayList;
import java.util.List;

public class KanoGenericType {

    public static void main(String[] args) {
        test();
    }


    public static void test(){




        /**
         * List 没有添加的赋值的限制
         * 可以添加任何元素
         */
//        List noneList = new ArrayList();
//        noneList.add(new Object()); noneList.add(new Music());
//        noneList.add(new Kano()); noneList.add(new Hanser());

        /**
         * 可以添加任意元素
         * 只能被无指定类型和 ? super Object 集合赋值
         */
//        List<? super Object> superObjectList = new ArrayList<>();
//        List<Object> anyList = new ArrayList<>();
//        anyList.add(new Music()); anyList.add(new Comic());
//        anyList.add(new Kano()); anyList.add(new Hanser());

        /**
         * 具体类型集合
         * 只能添加其和子类元素
         * 取值转为具体类型
         * 可以接受没有指定类型集合赋值
         */
//        List noneTypeList = new ArrayList<>();
        List<Music> musicList = new ArrayList<>();
        musicList.add(new Music());musicList.add(new Kano());

        /**
         * 可被任意集合赋值
         * 只能执行删除不能添加
         * 这个集合只能消费(删除),不能生产(添加)
         */

        List<Object> anyList = new ArrayList<>();
        List<?> genericList = new ArrayList<>();
        genericList.get(0);


        /**
         * 可以被T及子类，无类型,? extends 子类 集合赋值
         * 只能消费向上强转为 T
         * 不能添加
         */
        List<? extends Kano> kanoList = new ArrayList<>();
        List<Comic> comicList = new ArrayList<>();
        List noneTypeList = new ArrayList<>();
        List<? extends Music> extendList = new ArrayList<>();
        Music music = extendList.get(0);

        /**
         * 可以被出了 ? extend  外的集合赋值
         * 可用于生产添加 T 以及子类的元素
         * 取出元素为 Object
         */
        List<? super Music> superList = new ArrayList<>();
        superList.add(new Music()); superList.add(new Kano());
        superList.add(new Comic());

        /**
         * 强制赋值
         */
        extendList = (List<? extends Music>)superList;


    }

    static class Music { }
    static class Comic extends Music{}
    static class Kano extends Comic{}
    static class Hanser{}

}
