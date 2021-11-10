package com.kanonsd.loader;

import java.util.ServiceLoader;

public class Kano {

    public static void main(String[] args) throws Exception{

        KanoClassLoader kanoClassLoader = new KanoClassLoader();
        Class<?> myLoadParent = kanoClassLoader.findClass(SpiInterface.class.getName());

        Class<?> myLoad = kanoClassLoader.findClass(SpiIml.class.getName());


        Class<?> appLoadParent = Class.forName(SpiInterface.class.getName());

        Class<?> appLoad = Class.forName(SpiIml.class.getName());

        SpiInterface appInstance = (SpiInterface) appLoad.newInstance();

        SpiInterface myInstance = (SpiInterface) myLoad.newInstance();


        try {
            myLoad.cast(myInstance);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            appLoad.cast(appInstance);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("ServiceLoader = "+ServiceLoader.class.getClassLoader());


    }
}
