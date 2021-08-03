package com.kanozz.redis.slidewindow;

import redis.clients.jedis.Jedis;

public class KanoRedisZet {

    private static final String  IP = "127.0.0.1";
    private static final int PORT = 6379;

    public static Jedis create(){
        Jedis jedis = new Jedis(IP,PORT);
        return jedis;
    }




}
