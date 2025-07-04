package com.github.jakicdong.techub.service.config.service.Impl;

import com.github.jakicdong.techub.api.model.enums.ConfigTypeEnum;
import com.github.jakicdong.techub.api.model.vo.banner.dto.ConfigDTO;
import com.github.jakicdong.techub.service.config.repository.dao.ConfigDao;
import com.github.jakicdong.techub.service.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigDao configDao;

    @Override
    public List<ConfigDTO> getConfigList(ConfigTypeEnum configTypeEnum) {
        return configDao.listConfigByType(configTypeEnum.getCode());

    }

}
