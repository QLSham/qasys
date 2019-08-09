package com.ps.qa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description:多线程调用削峰
 * @author:QL
 * @create：2019/08/05
 */
@Configuration
@EnableAsync
public class ThreadPoolTaskExecutorAsyncPool {

    @Bean(name = "pool")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(20);
        executor.setKeepAliveSeconds(60);

        executor.setThreadNamePrefix("pool-a");
        //当任务数量超过MaxPoolSize和QueueCapacity时使用的策略，该策略是又调用任务的线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();

        return executor;
    }

}
