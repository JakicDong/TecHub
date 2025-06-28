package com.github.JakicDong.TecHub.core.util;


import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * @author JakicDong
 * @time 2025.6.27
 * @description Spring工具类
 * @version 1.0.0
 */
@Component
public class SpringUtil implements ApplicationContextAware, EnvironmentAware {
    //让 Bean 能获取到 Spring 容器的上下文（ApplicationContext）
    //让 Bean 能获取到环境配置（Environment）
    //volatile可以使得当一个线程修改volatile变量时，其他线程能立即看到最新
    private volatile static ApplicationContext context;
    private volatile static Environment environment;

    private static Binder binder;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.context = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        SpringUtil.environment = environment;
        binder = Binder.get(environment);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * 获取bean
     *
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> bean) {
        return context.getBean(bean);
    }

    public static <T> T getBeanOrNull(Class<T> bean) {
        try {
            return context.getBean(bean);
        } catch (Exception e) {
            return null;
        }
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static Object getBeanOrNull(String beanName) {
        try {
            return context.getBean(beanName);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取配置
     *
     * @param key
     * @return
     */
    public static String getConfig(String key) {
        return environment.getProperty(key);
    }
    public static String getConfigOrElse(String mainKey, String slaveKey) {
        String ans = environment.getProperty(mainKey);
        if (ans == null) {
            return environment.getProperty(slaveKey);
        }
        return ans;
    }
    /**
     * 获取配置
     *
     * @param key
     * @param val 配置不存在时的默认值
     * @return
     */
    public static String getConfig(String key, String val) {
        return environment.getProperty(key, val);
    }



    /**
     * 发布事件消息
     *
     * @param event
     */
    public static void publishEvent(ApplicationEvent event) {
        context.publishEvent(event);
    }


    /**
     * 配置绑定类
     *
     * @return
     */
    public static Binder getBinder() {
        return binder;
    }


}
