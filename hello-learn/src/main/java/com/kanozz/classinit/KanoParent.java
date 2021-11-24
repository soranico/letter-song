package com.kanozz.classinit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KanoParent {
    static {
        log.info("KanoParent Class init");
    }
}
