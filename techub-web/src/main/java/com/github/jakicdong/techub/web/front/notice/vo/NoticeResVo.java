package com.github.jakicdong.techub.web.front.notice.vo;

import com.github.jakicdong.techub.api.model.vo.PageListVo;
import com.github.jakicdong.techub.api.model.vo.notify.dto.NotifyMsgDTO;
import lombok.Data;

import java.util.Map;

/*
* @author JakicDong
* @description 通知消息返回VO
* @time 2025/9/6 14:41
*/
@Data
public class NoticeResVo {
    /**
     * 消息通知列表
     */
    private PageListVo<NotifyMsgDTO> list;

    /**
     * 每个分类的未读数量
     */
    private Map<String, Integer> unreadCountMap;

    /**
     * 当前选中的消息类型
     */
    private String selectType;
}
