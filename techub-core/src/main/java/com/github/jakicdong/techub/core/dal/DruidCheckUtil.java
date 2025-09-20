package com.github.jakicdong.techub.core.dal;

import com.github.hui.quick.plugin.qrcode.util.ClassUtils;

/*
* @author JakicDong
* @description 数据源检查工具类，用于判断是否包含durid相关的数据包
* @time 2025/9/20 18:56
*/
public class DruidCheckUtil {

    /**
     * 判断是否包含durid相关的数据包
     *
     * @return
     */
    public static boolean hasDuridPkg() {
        return ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource", DataSourceConfig.class.getClassLoader());
    }

}
