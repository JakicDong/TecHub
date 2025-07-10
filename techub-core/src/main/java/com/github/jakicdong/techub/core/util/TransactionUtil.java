package com.github.jakicdong.techub.core.util;

/*
* @author JakicDong
* @description 事务辅助工具类
* @time 2025/7/10 11:24
*/


import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionUtil {



    /**
     * 事务正常提交之后执行
     *
     * @param runnable
     */
    public static void registryAfterCommitOrImmediatelyRun(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        // 处于事务中
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            // 等事务提交之后执行
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    runnable.run();
                }
            });
        } else {
            // 马上执行
            runnable.run();
        }
    }

}

