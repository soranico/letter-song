package com.kanozz.cinit;

public class KanoB {
    static {
        System.out.println("KanoB Cinit");
        new KanoA();
    }
}
