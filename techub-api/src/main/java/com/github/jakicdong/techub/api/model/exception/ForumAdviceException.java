package com.github.jakicdong.techub.api.model.exception;

import com.github.jakicdong.techub.api.model.vo.Status;
import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;
import lombok.Getter;

/*
* @author JakicDong
* @description 业务异常类
* @time 2025/9/18 11:09
*/
public class ForumAdviceException extends RuntimeException {
    @Getter
    private Status status;

    public ForumAdviceException(Status status) {
        this.status = status;
    }

    public ForumAdviceException(int code, String msg) {
        this.status = Status.newStatus(code, msg);
    }

    public ForumAdviceException(StatusEnum statusEnum, Object... args) {
        this.status = Status.newStatus(statusEnum, args);
    }

}

