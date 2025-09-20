package com.github.jakicdong.techub.service.user.repository.params;

import com.github.jakicdong.techub.api.model.vo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
* @author JakicDong
* @description 搜索星球白名单参数
* @time 2025/9/20 20:21
*/
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchZsxqWhiteParams extends PageParam {

    /**
     * 审核状态
     */
    private Integer status;

    /**
     * 星球编号
     */
    private String starNumber;

    /**
     * 登录用户名
     */
    private String name;

    /**
     * 用户编号
     */
    private String userCode;

}