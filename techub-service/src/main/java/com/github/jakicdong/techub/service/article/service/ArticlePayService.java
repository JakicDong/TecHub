package com.github.jakicdong.techub.service.article.service;


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

}
