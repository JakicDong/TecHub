package com.github.jakicdong.techub.service.article.repository.params;

import com.github.jakicdong.techub.api.model.vo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
* @author JakicDong
* @description
* @time 2025/9/11 15:37
*/
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchCategoryParams extends PageParam {
    // 类目名称
    private String category;
}
