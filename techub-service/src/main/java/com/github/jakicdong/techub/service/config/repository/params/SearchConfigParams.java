package com.github.jakicdong.techub.service.config.repository.params;

import com.github.jakicdong.techub.api.model.vo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
* @author JakicDong
* @description 搜索配置参数
* @time 2025/9/20 19:46
*/
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchConfigParams extends PageParam {
    // 类型
    private Integer type;
    // 名称
    private String name;
}
