package com.github.jakicdong.techub.web.front.search.vo;

import com.github.jakicdong.techub.api.model.vo.user.dto.SimpleUserInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*
* @author JakicDong
* @description 搜索用户信息
* @time 2025/9/17 10:59
*/

@Data
@ApiModel(value="用户信息")
public class SearchUserVo implements Serializable {
    private static final long serialVersionUID = -2989169905031769195L;

    @ApiModelProperty("搜索的关键词")
    private String key;

    @ApiModelProperty("用户列表")
    private List<SimpleUserInfoDTO> items;
}
