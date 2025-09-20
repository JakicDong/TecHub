package com.github.jakicdong.techub.api.model.vo.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*
* @author JakicDong
* @description 批量操作用户req
* @time 2025/9/20 18:45
*/
@Data
public class ZsxqUserBatchOperateReq implements Serializable {
    // ids
    private List<Long> ids;
    // 状态
    private Integer status;
}
