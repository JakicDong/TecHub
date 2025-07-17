package com.github.jakicdong.techub.api.model.exception;

import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;



public class ExceptionUtil {

    public static ForumException of(StatusEnum status, Object... args) {
        return new ForumException(status, args);
    }

}
