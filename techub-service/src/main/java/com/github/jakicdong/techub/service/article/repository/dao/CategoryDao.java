package com.github.jakicdong.techub.service.article.repository.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.api.model.enums.PushStatusEnum;
import com.github.jakicdong.techub.api.model.enums.YesOrNoEnum;
import com.github.jakicdong.techub.service.article.repository.entity.CategoryDO;
import com.github.jakicdong.techub.service.article.repository.mapper.CategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* @author JakicDong
* @description 文章分类Dao
* @time 2025/7/3 12:18
*/
@Repository
public class CategoryDao extends ServiceImpl<CategoryMapper, CategoryDO> {
    /**
     * @return
     */
    public List<CategoryDO> listAllCategoriesFromDb() {
        return lambdaQuery()
                .eq(CategoryDO::getDeleted, YesOrNoEnum.NO.getCode())
                .eq(CategoryDO::getStatus, PushStatusEnum.ONLINE.getCode())
                .list();
    }


}
