package com.github.jakicdong.techub.api.model.vo.article.dto;

import lombok.Data;

import java.io.Serializable;

/*
* @author JakicDong
* @description 字典公共VO
* @time 2025/9/11 16:35
*/
@Data
public class DictCommonDTO implements Serializable {
    private static final long serialVersionUID = -8614833588325787479L;

    private String typeCode;

    private String dictCode;

    private String dictDesc;

    private Integer sortNo;
}
