package com.kanonormal.disruptor.simple;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.junit.Test;

import java.util.concurrent.Executors;

public class GoodsApp {

    @Test
    public void testSingle(){
        GoodsFactory goodsFactory = new GoodsFactory();
        int ringBufferSize = 1024;
        Disruptor<Goods> goodsDisruptor =new Disruptor<Goods>(goodsFactory,ringBufferSize, Executors.defaultThreadFactory(),
                ProducerType.SINGLE, new BlockingWaitStrategy());
        GoodsHandler goodsHandler = new GoodsHandler();
        goodsDisruptor.handleEventsWith(goodsHandler);
        goodsDisruptor.start();
        RingBuffer<Goods> ringBuffer = goodsDisruptor.getRingBuffer();
        int loop= 10;
        for (int i = 0; i < loop; i++) {
            long sequence = ringBuffer.next();
            Goods goods = ringBuffer.get(sequence);
            ringBuffer.publish(sequence);
        }
        goodsDisruptor.shutdown();
    }


}
