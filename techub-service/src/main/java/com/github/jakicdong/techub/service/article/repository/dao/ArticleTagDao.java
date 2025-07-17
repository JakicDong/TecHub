package com.github.jakicdong.techub.service.article.repository.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.api.model.vo.article.dto.TagDTO;
import com.github.jakicdong.techub.service.article.repository.entity.ArticleTagDO;
import com.github.jakicdong.techub.service.article.repository.mapper.ArticleTagMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* @author JakicDong
* @description 文章标签表
* @time 2025/7/3 20:21
*/

@Repository
public class ArticleTagDao extends ServiceImpl<ArticleTagMapper, ArticleTagDO> {

    /**
     * 查询文章标签
     *
     * @param articleId
     * @return
     */
    public List<TagDTO> queryArticleTagDetails(Long articleId) {
        return baseMapper.listArticleTagDetails(articleId);
    }

}
