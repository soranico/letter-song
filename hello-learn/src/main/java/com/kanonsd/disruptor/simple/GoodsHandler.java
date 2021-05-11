package com.kanonsd.disruptor.simple;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GoodsHandler implements EventHandler<Goods> {

    @Override
    public void onEvent(Goods goods, long sequence, boolean endOfBatch) throws Exception {
        log.info("sequence = {}",sequence);
        goods.setGoodsName("kano-"+sequence);
    }
}
