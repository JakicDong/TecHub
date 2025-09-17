package com.github.jakicdong.techub.service.config.repository.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.api.model.vo.article.dto.DictCommonDTO;
import com.github.jakicdong.techub.service.config.converter.DictCommonConverter;
import com.github.jakicdong.techub.service.config.repository.entity.DictCommonDO;
import com.github.jakicdong.techub.service.config.repository.mapper.DictCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* @author JakicDong
* @description 
* @time 2025/9/17 18:30
*/
@Repository
public class DictCommonDao extends ServiceImpl<DictCommonMapper, DictCommonDO> {

    /**
     * 获取所有字典列表
     * @return
     */
    public List<DictCommonDTO> getDictList() {
        List<DictCommonDO> list = lambdaQuery().list();
        return DictCommonConverter.toDTOS(list);
    }
}
