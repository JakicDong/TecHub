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

import java.util.List;

public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 根据三方唯一id进行查询
     *
     * @param accountId
     * @return
     */
    @Select("select * from user where third_account_id = #{account_id} limit 1")
    UserDO getByThirdAccountId(@Param("account_id") String accountId);


    /**
     * 遍历用户id
     *
     * @param offsetUserId
     * @param size
     * @return
     */
    @Select("select id from user where id > #{offsetUserId} order by id asc limit #{size}")
    List<Long> getUserIdsOrderByIdAsc(@Param("offsetUserId") Long offsetUserId, @Param("size") Long size);

}
