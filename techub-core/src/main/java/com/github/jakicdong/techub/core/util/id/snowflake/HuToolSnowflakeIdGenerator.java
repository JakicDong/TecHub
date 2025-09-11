package com.github.jakicdong.techub.core.util.id.snowflake;

import cn.hutool.core.lang.Snowflake;

import java.util.Date;


/*
* @author JakicDong
* @description hutool雪花算法id生成器
* @time 2025/9/11 16:25
*/
public class HuToolSnowflakeIdGenerator implements IdGenerator {
    private static final Date EPOC = new Date(2023, 1, 1);
    private Snowflake snowflake;

    public HuToolSnowflakeIdGenerator(int workId, int datacenter) {
        snowflake = new Snowflake(EPOC, workId, datacenter, false);
    }

    @Override
    public Long nextId() {
        return snowflake.nextId();
    }
}
