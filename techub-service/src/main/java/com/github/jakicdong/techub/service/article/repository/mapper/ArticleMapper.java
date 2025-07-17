package com.github.jakicdong.techub.service.article.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.article.dto.SimpleArticleDTO;
import com.github.jakicdong.techub.api.model.vo.article.dto.YearArticleDTO;
import com.github.jakicdong.techub.service.article.repository.entity.ArticleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
* @author JakicDong
* @description 文章mapper接口
* @time 2025/7/3 18:29
*/

public interface ArticleMapper extends BaseMapper<ArticleDO> {
    /**
     * 根据阅读次数获取热门文章
     *
     * @param pageParam
     * @return
     */
    List<SimpleArticleDTO> listArticlesByReadCounts(@Param("pageParam") PageParam pageParam);

    /**
     * 根据用户ID获取创作历程
     *
     * @param userId
     * @return
     */
    List<YearArticleDTO> listYearArticleByUserId(@Param("userId") Long userId);

}
