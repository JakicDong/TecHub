package com.github.jakicdong.techub.service.article.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jakicdong.techub.api.model.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/*
* @author JakicDong
* @description 类目DO实体
* @time 2025/7/3 12:22
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("category")
public class CategoryDO extends BaseDO {

    private static final long serialVersionUID = 1L;
    /**
     * 类目名称
     */
    private String categoryName;
    /**
     * 状态：0-未发布，1-已发布
     */
    private Integer status;
    /**
     * 排序
     */
    @TableField("`rank`")
    private Integer rank;

    private Integer deleted;

}
