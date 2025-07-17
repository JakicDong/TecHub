package com.github.jakicdong.techub.service.article.converter;

import com.github.jakicdong.techub.api.model.enums.SourceTypeEnum;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleDTO;
import com.github.jakicdong.techub.api.model.vo.article.dto.CategoryDTO;
import com.github.jakicdong.techub.core.util.PriceUtil;
import com.github.jakicdong.techub.service.article.repository.entity.ArticleDO;
import com.github.jakicdong.techub.service.article.repository.entity.CategoryDO;

/*
* @author JakicDong
* @description 文章转换
* @time 2025/7/3 12:58
*/
public class ArticleConverter {

    public static CategoryDTO toDto(CategoryDO category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategory(category.getCategoryName());
        dto.setCategoryId(category.getId());
        dto.setRank(category.getRank());
        dto.setStatus(category.getStatus());
        dto.setSelected(false);
        return dto;
    }
    public static ArticleDTO toDto(ArticleDO articleDO) {
        if (articleDO == null) {
            return null;
        }
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setAuthor(articleDO.getUserId());
        articleDTO.setArticleId(articleDO.getId());
        articleDTO.setArticleType(articleDO.getArticleType());
        articleDTO.setTitle(articleDO.getTitle());
        articleDTO.setShortTitle(articleDO.getShortTitle());
        articleDTO.setSummary(articleDO.getSummary());
        articleDTO.setCover(articleDO.getPicture());
        articleDTO.setSourceType(SourceTypeEnum.formCode(articleDO.getSource()).getDesc());
        articleDTO.setSourceUrl(articleDO.getSourceUrl());
        articleDTO.setStatus(articleDO.getStatus());
        articleDTO.setCreateTime(articleDO.getCreateTime().getTime());
        articleDTO.setLastUpdateTime(articleDO.getUpdateTime().getTime());
        articleDTO.setOfficalStat(articleDO.getOfficalStat());
        articleDTO.setToppingStat(articleDO.getToppingStat());
        articleDTO.setCreamStat(articleDO.getCreamStat());
        articleDTO.setReadType(articleDO.getReadType());
        articleDTO.setPayAmount(PriceUtil.toYuanPrice(articleDO.getPayAmount()));
        articleDTO.setPayWay(articleDO.getPayWay());

        // 设置类目id
        articleDTO.setCategory(new CategoryDTO(articleDO.getCategoryId(), null));
        return articleDTO;
    }

}
