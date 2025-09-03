package com.github.jakicdong.techub.service.article.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jakicdong.techub.service.article.repository.entity.ColumnArticleDO;
import org.apache.ibatis.annotations.Param;


public interface ColumnArticleMapper extends BaseMapper<ColumnArticleDO> {

    /**
     * 统计专栏的阅读人数
     *
     * @param columnId
     * @return
     */
    Long countColumnReadUserNums(@Param("columnId") Long columnId);


}