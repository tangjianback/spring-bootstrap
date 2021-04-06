package com.example.demo;

import com.example.dao.DataDeal;
import com.example.dao.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;

@SpringBootTest
class DemoApplicationTests {
    @Test
    /**
     * 连接池连接方式
     */
    public void demo2(){
        // 获取连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(30);
        // 设置最大的空闲连接数
        config.setMaxIdle(10);

        // 获得连接池: JedisPool jedisPool = new JedisPool(poolConfig,host,port);
        JedisPool jedisPool = new JedisPool(config,"localhost",6379);

        // 获得核心对象：jedis
        Jedis jedis = null;
        try{
            // 通过连接池来获得连接
            jedis = jedisPool.getResource();
            // 设置数据
            jedis.set("name","张三");
            // 获取数据
            String value = jedis.get("name");
            System.out.println(value);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // 释放资源
            if(jedis != null){
                jedis.close();
            }
            // 释放连接池
            if(jedisPool != null){
                jedisPool.close();
            }
        }

    }


    @Test
    public void redis_test()
    {
        // 1. 设置IP地址和端口
        Jedis jedis = new Jedis("localhost",6379);
        // 2. 保存数据
        //jedis.set("name",);
        // 3. 获取数据
        String value = jedis.get("name");
        System.out.println(value);
        // 4.释放资源
        jedis.close();
    }


//    private RedisTemplate redisTemplate;
//
//
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    public void setRedisTemplate(RedisTemplate redisTemplate) {
//        System.out.printf("注入");
//        this.redisTemplate = redisTemplate;
//    }
//    @Autowired
//    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
//        System.out.println("注入");
//        this.stringRedisTemplate = stringRedisTemplate;
//    }





    @Test
    void daotest(){
        DataDeal my_datadeal = new DataDeal();
        Student  s = new Student("mf20330075","老王", "男" ,"1967832","河南郑州","英国大学");

//		System.out.println("添加"+my_datadeal.addstudent(s));
//		System.out.println("删除"+my_datadeal.delete_student(s));
//		System.out.println("修改"+my_datadeal.edit_student(s));
        System.out.println("查询"+my_datadeal.get_student(s));
        //new DataDeal();
    }




}
