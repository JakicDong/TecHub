package com.github.jakicdong.techub.service.article.service.impl;

import com.github.jakicdong.techub.api.model.vo.PageVo;
import com.github.jakicdong.techub.api.model.vo.article.CategoryReq;
import com.github.jakicdong.techub.api.model.vo.article.SearchCategoryReq;
import com.github.jakicdong.techub.api.model.vo.article.dto.CategoryDTO;
import com.github.jakicdong.techub.core.util.NumUtil;
import com.github.jakicdong.techub.service.article.conveter.CategoryStructMapper;
import com.github.jakicdong.techub.service.article.repository.dao.CategoryDao;
import com.github.jakicdong.techub.service.article.repository.entity.CategoryDO;
import com.github.jakicdong.techub.service.article.repository.params.SearchCategoryParams;
import com.github.jakicdong.techub.service.article.service.CategoryService;
import com.github.jakicdong.techub.service.article.service.CategorySettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/*
* @author JakicDong
* @description 分类后台接口
* @time 2025/9/20 19:13
*/
@Service
public class CategorySettingServiceImpl implements CategorySettingService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void saveCategory(CategoryReq categoryReq) {
        CategoryDO categoryDO = CategoryStructMapper.INSTANCE.toDO(categoryReq);
        if (NumUtil.nullOrZero(categoryReq.getCategoryId())) {
            categoryDao.save(categoryDO);
        } else {
            categoryDO.setId(categoryReq.getCategoryId());
            categoryDao.updateById(categoryDO);
        }
        categoryService.refreshCache();
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        CategoryDO categoryDO = categoryDao.getById(categoryId);
        if (categoryDO != null){
            categoryDao.removeById(categoryDO);
        }
        categoryService.refreshCache();
    }

    @Override
    public void operateCategory(Integer categoryId, Integer pushStatus) {
        CategoryDO categoryDO = categoryDao.getById(categoryId);
        if (categoryDO != null){
            categoryDO.setStatus(pushStatus);
            categoryDao.updateById(categoryDO);
        }
        categoryService.refreshCache();
    }

    @Override
    public PageVo<CategoryDTO> getCategoryList(SearchCategoryReq req) {
        // 转换
        SearchCategoryParams params = CategoryStructMapper.INSTANCE.toSearchParams(req);
        // 查询
        List<CategoryDTO> categoryDTOS = categoryDao.listCategory(params);
        Long totalCount = categoryDao.countCategory(params);
        return PageVo.build(categoryDTOS, params.getPageSize(), params.getPageNum(), totalCount);
    }
}
