package com.kanozz.loader;

import com.kanozz.loader.data.KanoMusic;
import com.kanozz.loader.data.Music;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Kano implements KanoParent{


    public Music kano(ClassLoader classLoader) throws Exception{
        Class<?> hanserClazz = classLoader.loadClass("com.kanozz.loader.data.KanoMusic");
        KanoMusic kanoMusic =(KanoMusic) hanserClazz.newInstance();
        log.info("match = {}" ,kanoMusic instanceof KanoMusic);
        log.info("match = {}" ,kanoMusic instanceof Music);
        return kanoMusic;
    }

}
