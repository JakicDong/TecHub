package com.github.jakicdong.techub.service.statistics.repository.entitu;

/*
* @author JakicDong
* @description 请求计数表
* @time 2025/7/14 16:21
*/

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jakicdong.techub.api.model.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
@TableName("request_count")
public class RequestCountDO extends BaseDO {
    private static final long serialVersionUID = 1L;
    /**
     * 机器IP
     */
    private String host;

    /**
     * 访问计数
     */
    private Integer cnt;

    /**
     * 当前日期
     */
    private Date date;
}
