package com.github.jakicdong.techub.service.article.service;


import com.github.jakicdong.techub.api.model.vo.user.dto.SimpleUserInfoDTO;

import java.util.List;

/*
* @author JakicDong
* @description
* @time 2025/8/11 17:31
*/
public interface ArticlePayService {

    /**
     * 用户是否已经支付过了
     *
     * @param article
     * @param currentUerId
     * @return
     */
    boolean hasPayed(Long article, Long currentUerId);


    /**
     * 查询文章的打赏用户
     *
     * @param articleId 文章id
     * @return
     */
    List<SimpleUserInfoDTO> queryPayUsers(Long articleId);

}
