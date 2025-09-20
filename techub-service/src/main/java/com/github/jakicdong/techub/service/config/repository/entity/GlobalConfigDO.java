package com.github.jakicdong.techub.service.config.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jakicdong.techub.api.model.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
* @author JakicDong
* @description 评论表
* @time 2025/9/20 19:45
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("global_conf")
public class GlobalConfigDO extends BaseDO {
    private static final long serialVersionUID = -6122208316544171301L;

    // 配置项名称
    @TableField("`key`")
    private String key;
    // 配置项值
    private String value;
    // 备注
    private String comment;
    // 删除
    private Integer deleted;
}
