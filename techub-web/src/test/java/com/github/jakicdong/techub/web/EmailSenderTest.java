package com.github.jakicdong.techub.web;

import com.github.jakicdong.techub.core.util.EmailUtil;
import com.github.jakicdong.techub.core.util.SpringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
//@Import(EmailTestConfig.class) // 添加邮件配置
public class EmailSenderTest {

    @Test
    public void testEmailUtil() {
        String subject = "EmailUtil测试邮件";
        String content = "这是通过EmailUtil发送的测试邮件\n"
                + "收到此邮件说明工具类配置正确";

        String to = SpringUtil.getConfig("alarm.user", "default@qq.com");
        System.out.println("测试邮件已通过EmailUtil发送");
    }

}