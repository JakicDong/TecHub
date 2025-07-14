package com.github.jakicdong.techub.service.article.repository.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jakicdong.techub.api.model.enums.column.ColumnArticleReadEnum;
import com.github.jakicdong.techub.api.model.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/*
* @author JakicDong
* @description 专栏文章
* @time 2025/7/14 17:05
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("column_article")
public class ColumnArticleDO extends BaseDO {
    private static final long serialVersionUID = -2372103913090667453L;

    private Long columnId;

    private Long articleId;

    /**
     * 顺序，越小越靠前
     */
    private Integer section;

    /**
     * 专栏类型：免费、登录阅读、收费阅读等
     *
     * @see ColumnArticleReadEnum#getRead()
     */
    private Integer readType;
}
