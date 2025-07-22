package com.github.jakicdong.techub.core.cache;

import com.github.hui.quick.plugin.qrcode.util.json.JsonUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
* @author JakicDong
* @description redis客户端
* @time 2025/7/2 13:18
*/

/**
 * Redis客户端工具类，提供对Redis的常用操作封装
 * 特性：
 * 1. 自动添加前缀(th_)到所有key
 * 2. 支持字符串、对象自动序列化/反序列化
 * 3. 提供管道操作支持
 */
public class RedisClient {
    private static final Charset CODE = StandardCharsets.UTF_8; // 编码格式
    private static final String KEY_PREFIX = "th_"; // key前缀
    private static RedisTemplate<String, String> template; // Redis模板

    /**
     * 注册RedisTemplate实例
     * @param template Redis操作模板
     */
    public static void register(RedisTemplate<String, String> template) {
        RedisClient.template = template;
    }

    /**
     * 参数空值检查
     * @param args 需要检查的参数
     * @throws IllegalArgumentException 当参数为null时抛出
     */
    public static void nullCheck(Object... args) {
        for (Object obj : args) {
            if (obj == null) {
                throw new IllegalArgumentException("redis argument can not be null!");
            }
        }
    }

    /**
     * 创建管道操作实例
     * @return PipelineAction实例
     */
    public static PipelineAction pipelineAction() {
        return new PipelineAction();
    }

    /**
     * Hash结构字段自增
     * @param key Redis key
     * @param filed Hash字段
     * @param cnt 增量值
     * @return 自增后的值
     */
    public static Long hIncr(String key, String filed, Integer cnt) {
        return template.execute((RedisCallback<Long>) con -> con.hIncrBy(keyBytes(key), valBytes(filed), cnt));
    }

    /**
     * Redis管道操作封装类
     * 使用示例：
     * RedisClient.pipelineAction()
     *     .add("key1", (conn, key) -> conn.set(key, valBytes("value")))
     *     .add("key2", "field", (conn, key, field) -> conn.hSet(key, field, valBytes("value")))
     *     .execute();
     */
    public static class PipelineAction {
        private List<Runnable> run = new ArrayList<>();

        private RedisConnection connection;

        public PipelineAction add(String key, BiConsumer<RedisConnection, byte[]> conn) {
            run.add(() -> conn.accept(connection, RedisClient.keyBytes(key)));
            return this;
        }

        public PipelineAction add(String key, String field, ThreeConsumer<RedisConnection, byte[], byte[]> conn) {
            run.add(() -> conn.accept(connection, RedisClient.keyBytes(key), valBytes(field)));
            return this;
        }

        public void execute() {
            template.executePipelined((RedisCallback<Object>) connection -> {
                PipelineAction.this.connection = connection;
                run.forEach(Runnable::run);
                return null;
            });
        }
    }

    @FunctionalInterface
    public interface ThreeConsumer<T, U, P> {
        void accept(T t, U u, P p);
    }

    /**
     * 生成techub的缓存key
     *
     * @param key
     * @return
     */
    public static byte[] keyBytes(String key) {
        nullCheck(key);
        key = KEY_PREFIX + key;
        return key.getBytes(CODE);
    }

    private static <T> T toObj(byte[] ans, Class<T> clz) {
        if (ans == null) {
            return null;
        }

        if (clz == String.class) {
            return (T) new String(ans, CODE);
        }

        return JsonUtil.toObj(new String(ans, CODE), clz);
    }


    /**
     * 找出排名靠前的n个
     *
     * @param key
     * @param n
     * @return
     */
    public static List<ImmutablePair<String, Double>> zTopNScore(String key, int n) {

        return template.execute(new RedisCallback<List<ImmutablePair<String, Double>>>() {
            @Override
            public List<ImmutablePair<String, Double>> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<RedisZSetCommands.Tuple> set = connection.zRangeWithScores(keyBytes(key), -n, -1);
                if (set == null) {
                    return Collections.emptyList();
                }
                return set.stream()
                        .map(tuple -> ImmutablePair.of(toObj(tuple.getValue(), String.class), tuple.getScore()))
                        .sorted((o1, o2) -> Double.compare(o2.getRight(), o1.getRight())).collect(Collectors.toList());
            }
        });
    }

    public static <T> Map<String, T> hGetAll(String key, Class<T> clz) {
        Map<byte[], byte[]> records = template.execute((RedisCallback<Map<byte[], byte[]>>) con -> con.hGetAll(keyBytes(key)));
        if (records == null) {
            return Collections.emptyMap();
        }

        Map<String, T> result = Maps.newHashMapWithExpectedSize(records.size());
        for (Map.Entry<byte[], byte[]> entry : records.entrySet()) {
            if (entry.getKey() == null) {
                continue;
            }

            result.put(new String(entry.getKey()), toObj(entry.getValue(), clz));
        }
        return result;
    }

    public static <T> T hGet(String key, String field, Class<T> clz) {
        return template.execute((RedisCallback<T>) con -> {
            byte[] records = con.hGet(keyBytes(key), valBytes(field));
            if (records == null) {
                return null;
            }
            return toObj(records, clz);
        });
    }

    /**
     * techub的缓存值序列化处理
     *
     * @param val
     * @param <T>
     * @return
     */
    public static <T> byte[] valBytes(T val) {

        if (val instanceof String) {
            return ((String) val).getBytes(CODE);
        } else {
            return JsonUtil.toStr(val).getBytes(CODE);
        }
    }

    public static <T> Boolean hSet(String key, String field, T ans) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.hSet(keyBytes(key), valBytes(field), valBytes(ans));
            }
        });
    }

    /**
     * 设置缓存有效期
     *
     * @param key
     * @param expire 有效期，s为单位
     */
    public static void expire(String key, Long expire) {
        template.execute((RedisCallback<Void>) connection -> {
            connection.expire(keyBytes(key), expire);
            return null;
        });
    }

    /**
     * 分数更新
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public static Double zIncrBy(String key, String value, Integer score) {
        return template.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.zIncrBy(keyBytes(key), score, valBytes(value));
            }
        });
    }

    /**
     * 返回key的有效期
     *
     * @param key
     * @return
     */
    public static Long ttl(String key) {
        return template.execute((RedisCallback<Long>) con -> con.ttl(keyBytes(key)));
    }

    public static <T> Boolean hDel(String key, String field) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hDel(keyBytes(key), valBytes(field)) > 0;
            }
        });
    }

    /**
     * 带过期时间的缓存写入
     *
     * @param key
     * @param value
     * @param expire s为单位
     * @return
     */
    public static Boolean setStrWithExpire(String key, String value, Long expire) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.setEx(keyBytes(key), expire, valBytes(value));
            }
        });
    }


    /**
     * 删除缓存
     *
     * @param key
     */
    public static void del(String key) {
        template.execute((RedisCallback<Long>) con -> con.del(keyBytes(key)));
    }

    /**
     * 查询缓存
     *
     * @param key
     * @return
     */
    public static String getStr(String key) {
        return template.execute((RedisCallback<String>) con -> {
            byte[] val = con.get(keyBytes(key));
            return val == null ? null : new String(val);
        });
    }

    public static <T> Map<String, T> hMGet(String key, final List<String> fields, Class<T> clz) {
        return template.execute(new RedisCallback<Map<String, T>>() {
            @Override
            public Map<String, T> doInRedis(RedisConnection connection) throws DataAccessException {
                byte[][] f = new byte[fields.size()][];
                IntStream.range(0, fields.size()).forEach(i -> f[i] = valBytes(fields.get(i)));
                List<byte[]> ans = connection.hMGet(keyBytes(key), f);
                Map<String, T> result = Maps.newHashMapWithExpectedSize(fields.size());
                IntStream.range(0, fields.size()).forEach(i -> {
                    result.put(fields.get(i), toObj(ans.get(i), clz));
                });
                return result;
            }
        });
    }


}
