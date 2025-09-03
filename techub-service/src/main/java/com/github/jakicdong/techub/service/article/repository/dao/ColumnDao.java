package com.github.jakicdong.techub.service.article.repository.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.api.model.enums.column.ColumnStatusEnum;
import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.service.article.repository.entity.ColumnArticleDO;
import com.github.jakicdong.techub.service.article.repository.entity.ColumnInfoDO;
import com.github.jakicdong.techub.service.article.repository.mapper.ColumnArticleMapper;
import com.github.jakicdong.techub.service.article.repository.mapper.ColumnInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* @author JakicDong
* @description
* @time 2025/7/14 16:33
*/
@Repository
public class ColumnDao extends ServiceImpl<ColumnInfoMapper, ColumnInfoDO> {
    @Autowired
    private ColumnArticleMapper columnArticleMapper;


    public Long countColumnArticles() {
        return columnArticleMapper.selectCount(Wrappers.emptyWrapper());
    }


    /**
     * 统计专栏的文章数
     *
     * @return
     */
    public int countColumnArticles(Long columnId) {
        LambdaQueryWrapper<ColumnArticleDO> query = Wrappers.lambdaQuery();
        query.eq(ColumnArticleDO::getColumnId, columnId);
        return columnArticleMapper.selectCount(query).intValue();
    }

    /**
     * 分页查询专辑列表
     *
     * @param pageParam
     * @return
     */
    public List<ColumnInfoDO> listOnlineColumns(PageParam pageParam) {
        LambdaQueryWrapper<ColumnInfoDO> query = Wrappers.lambdaQuery();
        query.gt(ColumnInfoDO::getState, ColumnStatusEnum.OFFLINE.getCode())
                .last(PageParam.getLimitSql(pageParam))
                .orderByAsc(ColumnInfoDO::getSection);
        return baseMapper.selectList(query);
    }

    /**
     * 统计专栏的阅读人数
     * @return
     */
    public int countColumnReadPeoples(Long columnId) {
        return columnArticleMapper.countColumnReadUserNums(columnId).intValue();
    }


}
