package com.github.jakicdong.techub.core.dal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/*
* @author JakicDong
* @description 数据源切换注解，用于切换数据源
* @time 2025/9/20 18:56
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DsAno {
    /**
     * 启用的数据源，默认主库
     *
     * @return
     */
    MasterSlaveDsEnum value() default MasterSlaveDsEnum.MASTER;

    /**
     * 启用的数据源，如果存在，则优先使用它来替换默认的value
     *
     * @return
     */
    String ds() default "";
}
