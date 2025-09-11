package com.github.jakicdong.techub.core.config;

import lombok.Data;

/*
* @author JakicDong
* @description OSS相关配置
* @time 2025/9/10 15:37
*/
@Data
public class OssProperties {
    /**
     * 上传文件前缀路径
     */
    private String prefix;
    /**
     * oss类型
     */
    private String type;
    /**
     * 下面几个是oss的配置参数
     */
    private String endpoint;
    private String ak;
    private String sk;
    private String bucket;
    private String host;
}
