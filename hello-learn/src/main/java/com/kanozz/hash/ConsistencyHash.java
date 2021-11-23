package com.kanozz.hash;

import com.kanozz.loader.SpiInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ServiceLoader;

@Slf4j
public class ConsistencyHash {

    private int max = Integer.MAX_VALUE;

    @Test
    public void testHash(){
        ServiceLoader<SpiInterface> load = ServiceLoader.load(SpiInterface.class);
    }

}
