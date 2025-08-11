package com.github.jakicdong.techub.web.front.article.vo;

import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleDTO;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleOtherDTO;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticlePayInfoDTO;
import com.github.jakicdong.techub.api.model.vo.comment.dto.TopCommentDTO;
import com.github.jakicdong.techub.api.model.vo.recommend.SideBarDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.SimpleUserInfoDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserStatisticInfoDTO;
import lombok.Data;

import java.util.List;

/*
* @author JakicDong
* @description 文章细节VO
* @time 2025/8/9 19:57
*/
@Data
public class ArticleDetailVo {
    /**
     * 文章信息
     */
    private ArticleDTO article;

    /**
     * 评论信息
     */
    private List<TopCommentDTO> comments;

    /**
     * 热门评论
     */
    private TopCommentDTO hotComment;

    /**
     * 作者相关信息
     */
    private UserStatisticInfoDTO author;


    private ArticlePayInfoDTO payInfo;

    // 其他的信息，比如说翻页，比如说阅读类型
    private ArticleOtherDTO other;

    /**
     * 侧边栏信息
     */
    private List<SideBarDTO> sideBarItems;


    /**
     * 打赏用户列表
     */
    private List<SimpleUserInfoDTO> payUsers;
}
