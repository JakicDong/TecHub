package com.github.jakicdong.techub.core.util;

/*
* @author JakicDong
* @description 统计耗时工具类, 这个只支持同步的耗时打印，不支持异步的场景
* @time 2025/9/10 16:55
*/

import org.springframework.util.StopWatch;

import java.util.concurrent.Callable;

public class StopWatchUtil {
    private StopWatch stopWatch;
    private StopWatchUtil(String task) {
        stopWatch = task == null ? new StopWatch() : new StopWatch(task);
    }

    /**
     * 初始化
     *
     * @param task
     * @return
     */
    public static StopWatchUtil init(String... task) {
        return new StopWatchUtil(task.length > 0 ? task[0] : null);
    }

    /**
     * 同步耗时计时
     *
     * @param task 任务名
     * @param call 执行业务逻辑
     * @param <T>  返回类型
     * @return 返回结果
     */
    public <T> T record(String task, Callable<T> call) {
        stopWatch.start(task);
        try {
            return call.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * 同步耗时计时
     *
     * @param task 任务名
     * @param run  执行业务逻辑
     */
    public void record(String task, Runnable run) {
        stopWatch.start(task);
        try {
            run.run();
        } finally {
            stopWatch.stop();
        }
    }


    /**
     * 计时信息输出
     *
     * @return
     */
    public String prettyPrint() {
        return stopWatch.prettyPrint();
    }


}
