package com.github.jakicdong.techub.api.model.exception;

/*
* @author JakicDong
* @description 未命中异常
* @time 2025/7/11 15:09
*/

public class NoVlaInGuavaException extends RuntimeException {
    public NoVlaInGuavaException(String msg) {
        super(msg);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
