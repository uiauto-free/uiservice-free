package com.uiautofree.common.config.threadpool;

import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class FreeThreadPool {
    public static ExecutorService getExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, int queueSize, String name) {
        return new FreeThreadExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingDeque<>(queueSize),
                new ThreadFactoryBuilder().setNameFormat(name +"-%d").build(), new ThreadPoolExecutor.AbortPolicy());
    }
}
