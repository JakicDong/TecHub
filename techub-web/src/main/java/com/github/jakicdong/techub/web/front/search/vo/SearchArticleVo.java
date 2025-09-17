package com.github.jakicdong.techub.web.front.search.vo;

import com.github.jakicdong.techub.api.model.vo.article.dto.SimpleArticleDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*
* @author JakicDong
* @description 搜索文章VO
* @time 2025/9/17 10:58
*/
@Data
@ApiModel(value="文章信息")
public class SearchArticleVo implements Serializable {
    private static final long serialVersionUID = -2989169905031769195L;

    @ApiModelProperty("搜索的关键词")
    private String key;

    @ApiModelProperty("文章列表")
    private List<SimpleArticleDTO> items;
}
