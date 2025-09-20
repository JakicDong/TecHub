package com.github.jakicdong.techub.core.autoconf;

import com.github.jakicdong.techub.api.model.event.ConfigRefreshEvent;
import com.github.jakicdong.techub.core.autoconf.property.SpringValueRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/*
* @author JakicDong
* @description 配置刷新事件监听
* @time 2025/9/20 18:54
*/
@Service
public class ConfigRefreshEventListener implements ApplicationListener<ConfigRefreshEvent> {
    @Autowired
    private DynamicConfigContainer dynamicConfigContainer;

    /**
     * 监听配置变更事件
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ConfigRefreshEvent event) {
        dynamicConfigContainer.reloadConfig();
        SpringValueRegistry.updateValue(event.getKey());
    }
}
