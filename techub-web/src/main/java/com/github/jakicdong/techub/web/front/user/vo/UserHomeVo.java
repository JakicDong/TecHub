package com.github.jakicdong.techub.web.front.user.vo;

import com.github.jakicdong.techub.api.model.vo.PageListVo;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleDTO;
import com.github.jakicdong.techub.api.model.vo.article.dto.TagSelectDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.FollowUserInfoDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserPayCodeDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserStatisticInfoDTO;
import com.github.jakicdong.techub.api.model.enums.FollowSelectEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserHomeVo {
    private String homeSelectType;
    private List<TagSelectDTO> homeSelectTags;
    /**
     * 关注列表/粉丝列表
     */
    private PageListVo<FollowUserInfoDTO> followList;
    /**
     * @see FollowSelectEnum#getCode()
     */
    private String followSelectType;
    private List<TagSelectDTO> followSelectTags;
    private UserStatisticInfoDTO userHome;

    /**
     * 文章列表
     */
    private PageListVo<ArticleDTO> homeSelectList;

    /**
     * 收款二维码
     */
    private Map<String, UserPayCodeDTO> payQrCodes;
}