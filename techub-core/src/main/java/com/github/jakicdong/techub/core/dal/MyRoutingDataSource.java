package com.github.jakicdong.techub.core.dal;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;
/*
* @author JakicDong
* @description 自定义路由数据源，用于根据数据源类型切换数据源
* @time 2025/9/20 18:57
*/
public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return DsContextHolder.get();
    }

}
