package com.kanonormal.disruptor.simple;

import com.lmax.disruptor.EventFactory;

public class GoodsFactory implements EventFactory<Goods> {
    @Override
    public Goods newInstance() {
        return new Goods();
    }
}
