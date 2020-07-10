package com.Jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 *该类操作redis的第2个库,用来缓存ela的数据
 *
 *
 *
 * */
public class OptEla {
    private static JedisPool elaJedisPool = OptUserJedis.jedisPool;

}
