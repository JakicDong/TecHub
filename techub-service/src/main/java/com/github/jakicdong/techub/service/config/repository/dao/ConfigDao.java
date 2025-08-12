package com.github.jakicdong.techub.service.config.repository.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.api.model.enums.PushStatusEnum;
import com.github.jakicdong.techub.api.model.enums.YesOrNoEnum;
import com.github.jakicdong.techub.api.model.vo.banner.dto.ConfigDTO;
import com.github.jakicdong.techub.service.config.converter.ConfigConverter;
import com.github.jakicdong.techub.service.config.repository.entity.ConfigDO;
import com.github.jakicdong.techub.service.config.repository.mapper.ConfigMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* @author JakicDong
* @description
* @time 2025/7/3 18:58
*/

@Repository
public class ConfigDao extends ServiceImpl<ConfigMapper, ConfigDO> {

    /**
     * 根据类型获取配置列表（无需分页）
     *
     * @param type
     * @return
     */
    public List<ConfigDTO> listConfigByType(Integer type) {
        List<ConfigDO> configDOS = lambdaQuery()
                .eq(ConfigDO::getType, type)
                .eq(ConfigDO::getStatus, PushStatusEnum.ONLINE.getCode())
                .eq(ConfigDO::getDeleted, YesOrNoEnum.NO.getCode())
                .orderByAsc(ConfigDO::getRank)
                .list();
        return ConfigConverter.toDTOS(configDOS);
    }

    /**
     * 更新阅读相关计数
     */
    public void updatePdfConfigVisitNum(long configId, String extra) {
        lambdaUpdate().set(ConfigDO::getExtra, extra)
                .eq(ConfigDO::getId, configId)
                .update();
    }
}
