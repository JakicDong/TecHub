package com.github.jakicdong.techub.core.util.id.snowflake;


/*
* @author JakicDong
* @description 分布式id生成器
* @time 2025/9/11 16:25
*/
public interface IdGenerator {
    /**
     * 生成分布式id
     *
     * @return
     */
    Long nextId();
}
