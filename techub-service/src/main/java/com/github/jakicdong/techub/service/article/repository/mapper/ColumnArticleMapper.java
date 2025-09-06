package com.github.jakicdong.techub.service.article.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jakicdong.techub.api.model.vo.article.dto.SimpleArticleDTO;
import com.github.jakicdong.techub.service.article.repository.entity.ColumnArticleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ColumnArticleMapper extends BaseMapper<ColumnArticleDO> {

    /**
     * 统计专栏的阅读人数
     *
     * @param columnId
     * @return
     */
    Long countColumnReadUserNums(@Param("columnId") Long columnId);

    /**
     * 查询文章
     *
     * @param columnId
     * @param section
     * @return
     */
    ColumnArticleDO getColumnArticle(@Param("columnId") Long columnId, @Param("section") Integer section);

    /**
     * 查询文章列表
     *
     * @param columnId
     * @return
     */
    List<SimpleArticleDTO> listColumnArticles(@Param("columnId") Long columnId);

}