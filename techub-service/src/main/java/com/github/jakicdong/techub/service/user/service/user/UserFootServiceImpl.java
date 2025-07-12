package com.github.jakicdong.techub.service.user.service.user;


import com.github.jakicdong.techub.api.model.vo.user.dto.UserFootStatisticDTO;
import com.github.jakicdong.techub.service.user.repository.dao.UserFootDao;
import com.github.jakicdong.techub.service.user.service.UserFootService;
import org.springframework.stereotype.Service;

/*
* @author JakicDong
* @description 用户足迹Service实现类
* @time 2025/7/3 20:30
*/
@Service
public class UserFootServiceImpl implements UserFootService {
    private final UserFootDao userFootDao;

    public UserFootServiceImpl(UserFootDao userFootDao){
        this.userFootDao = userFootDao;
    }

    @Override
    public UserFootStatisticDTO getFootCount(){
        return userFootDao.getFootCount();
    }
}
