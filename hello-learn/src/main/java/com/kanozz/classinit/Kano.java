package com.kanozz.classinit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Kano extends KanoParent{

    private static final String name = "kano";

    static {
        log.info("Kano Class init");
    }
}
