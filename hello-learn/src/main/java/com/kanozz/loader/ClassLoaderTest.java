package com.kanozz.loader;

import com.kanozz.loader.data.Music;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        /**
         * 自定义类加载器加载类
         */
        KanoClassLoader kanoClassLoader = new KanoClassLoader();
        Class<?> kanoClazz = kanoClassLoader.loadClass("com.kanozz.loader.Kano");
        KanoParent kano = new Kano();
        /**
         *
         * 无法赋值因为对于当前方法所在的类而言
         * Kano这个类的加载是由APP加载的
         * 而对于kanoClazz而言是由自定义加载的
         * 因此两者类型不同无法赋值
         * -- 会抛出类型不匹配异常 ClassCastException
         * Kano kano = (Kano) kanoClazz.newInstance();
         */

        /**
         * 可以使用反射调用同个类加载的方法来实现赋值
         * 因为如果没有执行类加载那么对于当前类内声明的其他类
         * 而言都会使用当前类的类加载器进行加载
         */
        safe(()->{
            Method method = kanoClazz.getDeclaredMethod("kano",new Class[]{ClassLoader.class});
            method.setAccessible(true);
            Object invoke = method.invoke(kanoClazz.newInstance(), new Object[]{kanoClassLoader});
            log.info("match = false" , invoke instanceof Music);
        });



    }
    @FunctionalInterface
    private static interface Task{
        void run() throws Throwable;
    }

    private static void safe(Task task){
        try {
            task.run();
        }catch (Throwable e){
            log.error("",e);
        }
    }
}
