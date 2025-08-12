package com.github.jakicdong.techub.service.comment.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jakicdong.techub.service.comment.repository.eneity.CommentDO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface CommentMapper extends BaseMapper<CommentDO> {
    Map<String, Object> getHotTopCommentId(@Param("articleId") Long articleId);
}
