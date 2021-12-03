package com.kanozz.equalshash;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EqualsHashTest {

    /**
     * 只重写hash则如果属性相同的话
     * 则hash值会相同
     */
    @Test
    public void testHash() throws Exception{
        Map<KanoWithHash, String > cache = new HashMap<>();
        KanoWithHash first = new KanoWithHash("kano",18),second = new KanoWithHash("kano",18);
        cache.put(first,"kano");
        log.info("v = {}",cache.get(second));
        log.info("first hash = {}",first.hashCode());
        Method hashCode = KanoWithHash.class.getMethod("hashCode");
        hashCode.setAccessible(true);
        log.info("reflect hash = {}",hashCode.invoke(first));
        log.info("identityHashCode hash = {}",System.identityHashCode(first));
        log.info("identityHashCode hash = {}",System.identityHashCode(first));
        log.info("first hash = {}",first.hashCode());
        log.info("second hash = {}",second.hashCode());
    }


    @Test
    public void testEquals() throws Exception{
        Map<KanoWithEquals, String > cache = new HashMap<>();
        KanoWithEquals first = new KanoWithEquals("kano",18),second = new KanoWithEquals("kano",18);
        cache.put(first,"kano");
        log.info("v = {}",cache.get(second));
        log.info("equals = {}",first.equals(second));
        log.info("first hash = {}",first.hashCode());
        log.info("second hash = {}",second.hashCode());
    }

}
