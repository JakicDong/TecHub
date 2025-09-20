package com.github.jakicdong.techub.web.common;

import com.github.jakicdong.techub.api.model.vo.ResVo;
import com.github.jakicdong.techub.core.permission.Permission;
import com.github.jakicdong.techub.core.permission.UserRole;
import com.github.jakicdong.techub.service.config.service.DictCommonService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/*
* @author JakicDong
* @description 通用
* @time 2025/9/20 20:30
*/
@RestController
@Slf4j
@Permission(role = UserRole.LOGIN)
@Api(value = "通用接口管理控制器", tags = "全局设置")
@RequestMapping(path = {"common/","api/admin/common/", "admin/common/"})
public class DictCommonController {

    @Autowired
    private DictCommonService dictCommonService;

    @ResponseBody
    @GetMapping(path = "/dict")
    public ResVo<Map<String, Object>> list() {
        log.debug("获取字典");
        Map<String, Object> bannerDTOPageVo = dictCommonService.getDict();
        return ResVo.ok(bannerDTOPageVo);
    }
}
