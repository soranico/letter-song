package com.kanozz.cinit;

public class KanoA {
    static {
        System.out.println("KanoA Cinit");
        new KanoB();
    }
}
