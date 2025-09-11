package com.github.jakicdong.techub.web.front.article.vo;

import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleDTO;
import com.github.jakicdong.techub.api.model.vo.article.dto.CategoryDTO;
import com.github.jakicdong.techub.api.model.vo.article.dto.TagDTO;
import lombok.Data;

import java.util.List;

/*
* @author JakicDong
* @description 文章编辑页VO
* @time 2025/9/11 13:53
*/
@Data
public class ArticleEditVo {

    private ArticleDTO article;

    private List<CategoryDTO> categories;

    private List<TagDTO> tags;

}
