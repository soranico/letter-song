package com.kanozz.redis.slidewindow;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestRedis {



    @Test
    public void testBrPop(){
        Jedis jedis = RedisUtil.create();
        List<String> brpop = jedis.brpop("kano:brpop","0");
        for (String s : brpop) {
            System.out.println(s);
        }
    }

    @Test
    public void testBlPop(){
        Jedis jedis = RedisUtil.create();
        List<String> brpop = jedis.blpop("kano:brpop","0");
        for (String s : brpop) {
            System.out.println(s);
        }
    }

    @Test
    public void testLPush(){
        Jedis jedis = RedisUtil.create();
        jedis.lpush("kano:brpop","kano");
        jedis.lpush("kano:brpop","kano");
    }


    @Test
    public void testRPush(){
        Jedis jedis = RedisUtil.create();
        jedis.rpush("kano:brpop","kano");
        jedis.rpush("kano:brpop","kano");
    }


    @Test
    public void testZSetDelayProducer(){
        Jedis jedis = RedisUtil.create();
        for (int i = 0; i < 10; i++) {
            jedis.zadd("kano:delay",(double) (System.currentTimeMillis()+i),"delay:"+i);
        }
    }

    @Test
    public void testZSetDelayConsumer(){
        Jedis jedis = RedisUtil.create();
        while (true){

            Set<Tuple> delaySet = jedis.zrangeByScoreWithScores("kano:delay", 0, (double) System.currentTimeMillis());
            if (delaySet.isEmpty()){
                sleep(1);
            }
            for (Tuple delay : delaySet) {
                Long success = jedis.zrem("kano:delay", delay.getElement());
                if (success != null && success >0){
                    System.out.println("consumer = "+ delay.getElement() + " score = "+delay.getScore());
                }
            }
        }
    }


    @Test
    public void testSubscribe(){
        Jedis jedis = RedisUtil.create();
        JedisPubSub pubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("channel = "+channel + ", message = "+message);
            }
        };
        jedis.subscribe(pubSub,"kano:subscribe");
    }

    @Test
    public void testPublish(){
        Jedis jedis = RedisUtil.create();
        for (int i = 0; i < 10; i++) {
            Long publish = jedis.publish("kano:subscribe", "kano:" + i);
            if (publish == null || publish == 0){
                System.err.println("publish failed");
            }
            sleep(2);
        }
    }


    @Test
    public void testTransaction(){
        Jedis jedis = RedisUtil.create();
        try (Transaction transaction = jedis.multi()){
            transaction.set("kano:string","kano");
            /**
             * 执行异常也不会阻塞后续执行
             */
            transaction.incrBy("kano:string",1);

            transaction.hset("kano:hash","kano:hash:key","kano");
            transaction.lpush("kano:list","kano");
            transaction.rpush("kano:list","kano");
            transaction.sadd("kano:set","kano");
            transaction.zadd("kano:zset",10.0,"kano");
            transaction.exec();
        }

    }




    public void sleep(long second){
        try {
            TimeUnit.SECONDS.sleep(second);
        }catch (Exception e){

        }
    }




}
