package com.github.jakicdong.techub.core.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
* @author JakicDong
* @description 图片相关配置类
* @time 2025/9/10 15:36
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "image")
public class ImageProperties {
    /**
     * 存储绝对路径
     */
    private String absTmpPath;

    /**
     * 存储相对路径
     */
    private String webImgPath;

    /**
     * 上传文件的临时存储目录
     */
    private String tmpUploadPath;

    /**
     * 访问图片的host
     */
    private String cdnHost;

    private OssProperties oss;

    public String buildImgUrl(String url) {
        if (!url.startsWith(cdnHost)) {
            return cdnHost + url;
        }
        return url;
    }
}
