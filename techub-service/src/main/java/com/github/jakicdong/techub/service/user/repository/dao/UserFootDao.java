package com.github.jakicdong.techub.service.user.repository.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserFootStatisticDTO;
import com.github.jakicdong.techub.service.user.repository.entity.UserFootDO;
import com.github.jakicdong.techub.service.user.repository.mapper.UserFootMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserFootDao extends ServiceImpl<UserFootMapper, UserFootDO> {





    //获取用户主页的点赞数、收藏数、留言数、阅读数
    public UserFootStatisticDTO getFootCount(){
        return baseMapper.getFootCount();
    }

}
