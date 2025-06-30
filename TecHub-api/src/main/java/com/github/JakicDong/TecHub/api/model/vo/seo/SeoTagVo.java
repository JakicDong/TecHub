package com.github.JakicDong.TecHub.api.model.vo.seo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JakicDong
 * @date 2025.6.30
 * @description seo标签
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeoTagVo {

    private String key;

    private String val;
}
