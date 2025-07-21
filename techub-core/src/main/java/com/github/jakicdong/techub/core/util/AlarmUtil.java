package com.github.jakicdong.techub.core.util;

/*
* @author JakicDong
* @description 警告工具类
* @time 2025/7/17 14:58
*/


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.github.jakicdong.techub.core.util.SpringUtil;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
public class AlarmUtil extends AppenderBase<ILoggingEvent> {
    private static final long INTERVAL = 10 * 1000 * 60;
    private long lastAlarmTime = 0;

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        if (canAlarm()) {
            try {
                System.out.println(SpringUtil.getEnvironment());
                boolean sent = EmailUtil.sendMail(iLoggingEvent.getLoggerName(),
                        SpringUtil.getConfig("alarm.user", "jakic2062966830@163.com"),
                        iLoggingEvent.getFormattedMessage()
                );
                System.out.println("测试邮件发送结果: " + (sent ? "成功" : "失败"));
            } catch (Exception e) {
                System.err.println("邮件发送异常:");
                e.printStackTrace();
            }
        }
    }

    private boolean canAlarm() {
        // 做一个简单的频率过滤,一分钟内只允许发送一条报警
        long now = System.currentTimeMillis();
        if (now - lastAlarmTime >= INTERVAL) {
            lastAlarmTime = now;
            return true;
        } else {
            return false;
        }
    }
}
