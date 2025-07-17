package com.github.jakicdong.techub.api.model.vo.article.dto;

import lombok.Data;

import java.io.Serializable;

/*
* @author JakicDong
* @description 文章标签DTO
* @time 2025/7/3 14:38
*/

@Data
public class TagDTO implements Serializable {
    private static final long serialVersionUID = -8614833588325787479L;

    private Long tagId;

    private String tag;

    private Integer status;

    private Boolean selected;
}
