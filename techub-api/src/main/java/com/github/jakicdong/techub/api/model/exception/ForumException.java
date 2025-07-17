package com.github.jakicdong.techub.api.model.exception;

import com.github.jakicdong.techub.api.model.vo.Status;
import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;
import lombok.Getter;

/*
* @author JakicDong
* @description 业务异常
* @time 2025/7/3 20:55
*/

public class ForumException extends RuntimeException {
    @Getter
    private Status status;

    public ForumException(Status status) {
        this.status = status;
    }

    public ForumException(int code, String msg) {
        this.status = Status.newStatus(code, msg);
    }

    public ForumException(StatusEnum statusEnum, Object... args) {
        this.status = Status.newStatus(statusEnum, args);
    }

}