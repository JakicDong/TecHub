package com.github.jakicdong.techub.service.user.repository.mapper;

/*
* @author JakicDong
* @description 用户用于登录的接口
* @time 2025/7/9 16:58
*/

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jakicdong.techub.service.user.repository.entity.UserDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<UserDO> {

    @Select("select * from user where third_account_id = #{account_id} limit 1")
    UserDO getByThirdAccountId(@Param("account_id") String accountId);

}
