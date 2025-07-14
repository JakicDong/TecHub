package com.github.jakicdong.techub.service.statistics.service.Impl;

import com.github.jakicdong.techub.api.model.vo.statistics.dto.StatisticsCountDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserFootStatisticDTO;
import com.github.jakicdong.techub.service.article.service.ArticleReadService;
import com.github.jakicdong.techub.service.article.service.ColumnService;
import com.github.jakicdong.techub.service.statistics.service.RequestCountService;
import com.github.jakicdong.techub.service.statistics.service.StatisticsSettingService;
import com.github.jakicdong.techub.service.user.service.UserFootService;
import com.github.jakicdong.techub.service.user.service.UserService;
import com.github.jakicdong.techub.service.user.service.conf.AiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
* @author JakicDong
* @description 数据统计后台接口
* @time 2025/7/12 09:15
*/
@Slf4j
@Service
public class StatisticsSettingServiceImpl implements StatisticsSettingService {
    @Autowired
    private RequestCountService requestCountService;

    @Autowired
    private UserService userService;

    @Autowired
    private ColumnService columnService;
    @Autowired
    private UserFootService userFootService;

    @Autowired
    private ArticleReadService articleReadService;

    @Resource
    private AiConfig aiConfig;





    @Override
    public StatisticsCountDTO getStatisticsCount() {
        // 从 user_foot 表中查询点赞数、收藏数、留言数、阅读数
        UserFootStatisticDTO userFootStatisticDTO =  userFootService.getFootCount();
        if (userFootStatisticDTO == null) {
            userFootStatisticDTO = new UserFootStatisticDTO();
        }
        String temp = "12345";
        /*
        * @author JakicDong
        * @description 2131
        * @time 2025/7/14 15:02
        */
        log.info(temp);


        return StatisticsCountDTO.builder()
                .userCount(userService.getUserCount())
                .articleCount(articleReadService.getArticleCount())
                .pvCount(requestCountService.getPvTotalCount())
                .tutorialCount(columnService.getTutorialCount())
                .commentCount(userFootStatisticDTO.getCommentCount())
                .collectCount(userFootStatisticDTO.getCollectionCount())
                .likeCount(userFootStatisticDTO.getPraiseCount())
                .readCount(userFootStatisticDTO.getReadCount())
                .starPayCount(aiConfig.getMaxNum().getStarNumber())
                .build();
    }





}
