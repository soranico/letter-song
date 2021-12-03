package com.kanozz.memoryleak;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.InputStream;

@Slf4j
public class MemoryLeak {


    @Test
    public void notCloseIo() {
        InputStream is = null;
        try {
            is = MemoryLeak.class.getResourceAsStream("/JmhSwitchAndIf.txt");
            int kano = 1/0;
        } catch (Exception e) {
            log.error("", e);
        }
        if (is!=null){
            try {
                BufferedInputStream reader = new BufferedInputStream(is);
                byte[] cache = new byte[1024];
                int stop ;
                while ((stop = reader.read(cache,0,1024))!=-1){
                    log.info("line = {}",new String(cache,0,stop));
                }
            }catch (Exception e){
                log.error("",e);
            }

        }
    }

}
