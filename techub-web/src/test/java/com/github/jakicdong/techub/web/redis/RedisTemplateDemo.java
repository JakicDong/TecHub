package com.github.jakicdong.techub.web.redis;

import com.github.jakicdong.techub.web.TecHubApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Set;

/*
* @author JakicDong
* @description 测试redis的类
* @time 2025/7/23 15:33
*/
@Slf4j
@SpringBootTest(classes = TecHubApplication.class)
public class RedisTemplateDemo {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 切换到指定数据库
     * @param dbIndex 数据库索引(0-15)
     */
    public void selectDatabase(int dbIndex) {
        if (dbIndex < 0 || dbIndex > 15) {
            throw new IllegalArgumentException("Redis数据库索引必须在0-15之间");
        }

        redisTemplate.execute((RedisCallback<Void>) connection -> {
            connection.select(dbIndex);
            return null;
        });

        stringRedisTemplate.execute((RedisCallback<Void>) connection -> {
            connection.select(dbIndex);
            return null;
        });
    }

    @Test
    public void testPut() {
        // 切换到数据库1
//        selectDatabase(2);

        //设置 key 和 value，并保存到 Redis 中
        // 1. 使用 StringRedisTemplate 写入
        stringRedisTemplate.opsForValue().set("dong", "jakic");
        redisTemplate.opsForValue().set("itwanger", "沉默王二");
        // 2. 立即查询验证
        String value = stringRedisTemplate.opsForValue().get("dong");
        String value2 = redisTemplate.opsForValue().get("itwanger").toString();
        System.out.println("查询结果: " + value);  // 应该输出 jakic
        System.out.println("查询结果: " + value2);
//        stringRedisTemplate.opsForList().rightPush("girl", "陈清扬");
//        stringRedisTemplate.opsForList().rightPush("girl", "小转玲");
//        stringRedisTemplate.opsForList().rightPush("girl", "茶花女");
    }

    @Test
    public void testGet() {
        //从 Redis 中获取 key 对应的 value
        Object value = redisTemplate.opsForValue().get("itwanger");
        System.out.println(value);

        List<String> girls = stringRedisTemplate.opsForList().range("girl", 0, -1);
        System.out.println(girls);

    }

    @Test
    public void testExecute() {
        // 使用 execute 方法执行 Redis 命令
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                // 执行 Redis 命令，例如 set 和 get 命令
                connection.set("itwanger".getBytes(), "沉默王二".getBytes());
                byte[] value = connection.get("itwanger".getBytes());
                String strValue = new String(value);
                // 输出获取到的值
                System.out.println(strValue);
                return null;
            }
        });
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void push(String key, String value) {
        stringRedisTemplate.opsForList().rightPush(key, value);
    }

    public List<String> range(String key, int start, int end) {
        return stringRedisTemplate.opsForList().range(key, start, end);
    }

    public void hset(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public Object hget(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public void sadd(String key, String value) {
        stringRedisTemplate.opsForSet().add(key, value);
    }

    public Set<String> smembers(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    public void zadd(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<Object> zrange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }
}
