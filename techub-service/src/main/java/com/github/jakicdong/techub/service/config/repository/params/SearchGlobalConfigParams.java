package com.github.jakicdong.techub.service.config.repository.params;

import com.github.jakicdong.techub.api.model.vo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;


/*
* @author JakicDong
* @description 全局配置表查询参数
* @time 2025/9/20 19:51
*/
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchGlobalConfigParams extends PageParam {
    // 配置项名称
    private String key;
    // 配置项值
    private String value;
    // 备注
    private String comment;
}
