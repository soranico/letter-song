package com.kanozz.memoryleak;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.InputStream;

@Slf4j
public class MemoryLeak {


    @Test
    public void notCloseIo(){
        try {
            while (true) {
                InputStream is = MemoryLeak.class.getResourceAsStream("/JmhSwitchAndIf.txt");
                BufferedInputStream fileIs = new BufferedInputStream(is);
            }
        }catch (Exception e){
            log.error("",e);
        }
    }

}
