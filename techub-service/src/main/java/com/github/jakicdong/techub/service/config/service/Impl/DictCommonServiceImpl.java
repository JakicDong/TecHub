package com.github.jakicdong.techub.service.config.service.Impl;

import com.github.jakicdong.techub.api.model.vo.article.dto.CategoryDTO;
import com.github.jakicdong.techub.api.model.vo.article.dto.DictCommonDTO;
import com.github.jakicdong.techub.service.article.service.CategoryService;
import com.github.jakicdong.techub.service.config.repository.dao.DictCommonDao;
import com.github.jakicdong.techub.service.config.service.DictCommonService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* @author JakicDong
* @description 字典Service
* @time 2025/9/17 18:29
*/
@Service
public class DictCommonServiceImpl implements DictCommonService {

    @Resource
    private DictCommonDao dictCommonDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Map<String, Object> getDict() {
        Map<String, Object> result = Maps.newLinkedHashMap();

        List<DictCommonDTO> dictCommonList = dictCommonDao.getDictList();

        Map<String, Map<String, String>> dictCommonMap = Maps.newLinkedHashMap();
        for (DictCommonDTO dictCommon : dictCommonList) {
                Map<String, String> codeMap = dictCommonMap.get(dictCommon.getTypeCode());
                if (codeMap == null || codeMap.isEmpty()) {
                    codeMap = Maps.newLinkedHashMap();
                    dictCommonMap.put(dictCommon.getTypeCode(), codeMap);
                }
                codeMap.put(dictCommon.getDictCode(), dictCommon.getDictDesc());
        }

        // 获取分类的字典信息
        List<CategoryDTO> categoryDTOS = categoryService.loadAllCategories();
        Map<String, String> codeMap = new HashMap<>();
        categoryDTOS.forEach(categoryDTO -> codeMap.put(categoryDTO.getCategoryId().toString(), categoryDTO.getCategory()));
        dictCommonMap.put("CategoryType", codeMap);

        result.putAll(dictCommonMap);
        return result;
    }

}
