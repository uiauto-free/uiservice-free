package com.uiautofree.common.config.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FreeThreadExecutor extends ThreadPoolExecutor {
    public FreeThreadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new FutureTask<T>(callable) {
            @Override
            public void run() {
                try {
                    super.run();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        };
    }
}
