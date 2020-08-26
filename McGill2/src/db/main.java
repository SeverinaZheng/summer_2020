package db;
import java.util.*;
import java.sql.*;
import redis.clients.jedis.*;


public class main {
    static RedisManager jedisManager;

    public static void main(String[] args) {
        jedisManager = RedisManager.getInstance();
        jedisManager.connect();
        Jedis jedis = jedisManager.getJedis();
        jedis.rpush("sda","www");
        jedisManager.release();
    }

}


