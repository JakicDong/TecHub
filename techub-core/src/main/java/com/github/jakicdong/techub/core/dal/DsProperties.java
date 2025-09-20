package com.github.jakicdong.techub.core.dal;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/*
* @author JakicDong
* @description 多数据源配置类，用于加载多数据源的配置
* @time 2025/9/20 18:57
*/
@Data
@ConfigurationProperties(prefix = DsProperties.DS_PREFIX)
public class DsProperties {
    public static final String DS_PREFIX = "spring.dynamic";
    /**
     * 默认数据源
     */
    private String primary;

    /**
     * 多数据源配置
     */
    private Map<String, DataSourceProperties> datasource;
}