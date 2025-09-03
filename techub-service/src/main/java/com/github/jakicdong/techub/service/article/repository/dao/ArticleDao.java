package com.github.jakicdong.techub.service.article.repository.dao;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.enums.DocumentTypeEnum;
import com.github.jakicdong.techub.api.model.enums.OfficalStatEnum;
import com.github.jakicdong.techub.api.model.enums.PushStatusEnum;
import com.github.jakicdong.techub.api.model.enums.YesOrNoEnum;
import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleDTO;
import com.github.jakicdong.techub.api.model.vo.article.dto.SimpleArticleDTO;
import com.github.jakicdong.techub.api.model.vo.article.dto.YearArticleDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.BaseUserInfoDTO;
import com.github.jakicdong.techub.core.permission.UserRole;
import com.github.jakicdong.techub.service.article.conveter.ArticleConverter;
import com.github.jakicdong.techub.service.article.repository.entity.ArticleDO;
import com.github.jakicdong.techub.service.article.repository.entity.ArticleDetailDO;
import com.github.jakicdong.techub.service.article.repository.entity.ReadCountDO;
import com.github.jakicdong.techub.service.article.repository.mapper.ArticleDetailMapper;
import com.github.jakicdong.techub.service.article.repository.mapper.ArticleMapper;
import com.github.jakicdong.techub.service.article.repository.mapper.ReadCountMapper;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ArticleDao extends ServiceImpl<ArticleMapper, ArticleDO> {
    @Resource
    private ArticleDetailMapper articleDetailMapper;
    @Resource
    private ReadCountMapper readCountMapper;
    /**
     * 按照分类统计文章的数量
     *
     * @return key: categoryId, value: count
     */
    public Map<Long, Long> countArticleByCategoryId() {
        QueryWrapper<ArticleDO> query = Wrappers.query();
        query.select("category_id, count(*) as cnt")
                .eq("deleted", YesOrNoEnum.NO.getCode())
                .eq("status", PushStatusEnum.ONLINE.getCode()).groupBy("category_id");
        List<Map<String, Object>> mapList = baseMapper.selectMaps(query);
        Map<Long, Long> result = Maps.newHashMapWithExpectedSize(mapList.size());
        for (Map<String, Object> mp : mapList) {
            Long cnt = (Long) mp.get("cnt");
            if (cnt != null && cnt > 0) {
                result.put((Long) mp.get("category_id"), cnt);
            }
        }
        return result;
    }

    /**
     * 热门文章推荐，适用于首页的侧边栏
     *
     * @param pageParam
     * @return
     */
    public List<SimpleArticleDTO> listHotArticles(PageParam pageParam) {
        return baseMapper.listArticlesByReadCounts(pageParam);

    }

    public List<ArticleDO> listArticlesByCategoryId(Long categoryId, PageParam pageParam) {
        if (categoryId != null && categoryId <= 0) {
            // 分类不存在时，表示查所有
            categoryId = null;
        }
        LambdaQueryWrapper<ArticleDO> query = Wrappers.lambdaQuery();
        query.eq(ArticleDO::getDeleted, YesOrNoEnum.NO.getCode())
                .eq(ArticleDO::getStatus, PushStatusEnum.ONLINE.getCode());

        // 如果分页中置顶的四条数据，需要加上官方的查询条件
        // 说明是查询官方的文章，非置顶的文章，只限制全部分类
        if (categoryId == null && pageParam.getPageSize() == PageParam.TOP_PAGE_SIZE) {
            query.eq(ArticleDO::getOfficalStat, OfficalStatEnum.OFFICAL.getCode());
        }

        Optional.ofNullable(categoryId).ifPresent(cid -> query.eq(ArticleDO::getCategoryId, cid));
        query.last(PageParam.getLimitSql(pageParam))
                .orderByDesc(ArticleDO::getToppingStat, ArticleDO::getCreateTime);
        return baseMapper.selectList(query);
    }
    /**
     * 根据用户ID获取创作历程
     *
     * @param userId
     * @return
     */
    public List<YearArticleDTO> listYearArticleByUserId(Long userId) {
        return baseMapper.listYearArticleByUserId(userId);
    }

    public Long countArticle(){
        return lambdaQuery()
                .eq(ArticleDO::getDeleted , YesOrNoEnum.NO.getCode())
                .count();
    }

    public ArticleDTO queryArticleDetail(Long articleId){
        //查询文章记录
        ArticleDO article = baseMapper.selectById(articleId);
        //判断是否是空的或者被删除了
        if (article == null || Objects.equals(article.getDeleted(), YesOrNoEnum.YES.getCode())) {
            return null;
        }
        //查询文章正文
        ArticleDTO dto = ArticleConverter.toDto(article);
        if (showReviewContent(article)) {
            ArticleDetailDO detail = findLatestDetail(articleId);
            dto.setContent(detail.getContent());
        } else {
            // 对于审核中的文章，只有作者本人才能看到原文
            dto.setContent("### 文章审核中，请稍后再看");
        }
        return dto;
    }
    /**
     * 判断是否展示审核中的内容
     *
     * @param article 文章实体
     * @return false 表示需要展示审核中的字样 | true 表示展示原文
     */
    private boolean showReviewContent(ArticleDO article) {
        if (article.getStatus() != PushStatusEnum.REVIEW.getCode()) {
            return true;
        }

        BaseUserInfoDTO user = ReqInfoContext.getReqInfo().getUser();
        if (user == null) {
            return false;
        }

        // 作者本人和admin超管可以看到审核内容
        return user.getUserId().equals(article.getUserId()) || (user.getRole() != null && user.getRole().equalsIgnoreCase(UserRole.ADMIN.name()));
    }

    // ------------ article content  ----------------

    private ArticleDetailDO findLatestDetail(long articleId) {
        // 查询文章内容
        LambdaQueryWrapper<ArticleDetailDO> contentQuery = Wrappers.lambdaQuery();
        contentQuery.eq(ArticleDetailDO::getDeleted, YesOrNoEnum.NO.getCode())
                .eq(ArticleDetailDO::getArticleId, articleId)
                .orderByDesc(ArticleDetailDO::getVersion);
        return articleDetailMapper.selectList(contentQuery).get(0);
    }

    /**
     * 阅读计数
     *
     * @param articleId
     * @return
     */
    public int incrReadCount(Long articleId) {
        LambdaQueryWrapper<ReadCountDO> query = Wrappers.lambdaQuery();
        query.eq(ReadCountDO::getDocumentId, articleId).eq(ReadCountDO::getDocumentType, DocumentTypeEnum.ARTICLE.getCode());
        ReadCountDO record = readCountMapper.selectOne(query);
        if (record == null) {
            record = new ReadCountDO().setDocumentId(articleId).setDocumentType(DocumentTypeEnum.ARTICLE.getCode()).setCnt(1);
            readCountMapper.insert(record);
        } else {
            // fixme: 这里存在并发覆盖问题，推荐使用 update read_count set cnt = cnt + 1 where id = xxx
            record.setCnt(record.getCnt() + 1);
            readCountMapper.updateById(record);
        }
        return record.getCnt();
    }


    /**
     * 作者的热门文章推荐，适用于作者的详情页侧边栏
     *
     * @param userId
     * @param pageParam
     * @return
     */
    public List<SimpleArticleDTO> listAuthorHotArticles(long userId, PageParam pageParam) {
        return baseMapper.listArticlesByUserIdOrderByReadCounts(userId, pageParam);
    }




}
