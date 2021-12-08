package com.kanozz.createobj;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

@Slf4j
public class CreateObjTest {



    @Test
    public void testClone() throws Exception{
       Kano kano = new Kano().setAge(18).setName("kano").setHanser(256);
       Kano clone = (Kano) kano.clone();
       // false
       log.info("same obj = {}",kano == clone);
       kano.setHanser(257);
       log.info("same = {}",kano.hanser.intValue() == clone.hanser.intValue());
    }


    @Test
    public void testUnSafe() throws Exception{
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        /** 不会执行构造方法 **/
        Kano kano = (Kano) unsafe.allocateInstance(Kano.class);

        kano.setAge(18).setName("kano").setHanser(256);
    }




    @Accessors(chain = true)
    @Data
    private static class Kano implements Cloneable{
        private int age;
        private String name;
        private Integer hanser;

        public Kano(){
           log.info("kano kano");
        }
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    private static class Parent{
        Parent(){
            log.info("parent");
        }
        private Integer age = 256;
    }


    private static class Child extends Parent{
        private Integer age;
    }

    @Test
    public void createOneObj(){
        /**
         * 只会创建一个 Child 对象
         * 虽然会调用父类的构造方法但是并没有创建父类对象
         * 调用构造方法不等于创建对象
         */
        Child child = new Child();

    }

}
