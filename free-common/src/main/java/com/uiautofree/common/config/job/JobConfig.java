package com.uiautofree.common.config.job;

import com.uiautofree.common.config.threadpool.FreeThreadExecutor;
import com.uiautofree.common.config.threadpool.FreeThreadPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class JobConfig {
    public static final String JOB_THREAD_POOL = "JobThreadPool";

    @Bean(name = JOB_THREAD_POOL)
    public Executor JobThreadPool() {
        return FreeThreadPool.getExecutor(10, 40, 60, 5000, "job-tp");
    }

}
