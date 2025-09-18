package com.github.jakicdong.techub.web.global;

import com.github.jakicdong.techub.api.model.exception.ForumAdviceException;
import com.github.jakicdong.techub.api.model.vo.ResVo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
* @author JakicDong
* @description 全局异常处理类
* @time 2025/9/18 11:08
*/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ForumAdviceException.class)
    public ResVo<String> handleForumAdviceException(ForumAdviceException e) {
        return ResVo.fail(e.getStatus());
    }
}
