package com.leikooo.yubi.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author leikooo
 * @Description
 */
@Configuration
public class ThreadPoolExecutorConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadFactory threadFactory = new ThreadFactory() {
            private int count = 0;
            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread thread = new Thread(r);
                count++;
                thread.setName("线程" + count);
                return thread;
            }
        };
        // 这几个参数解析一下
        /*
            1、corePoolSize 这个是最重要的一个参数，是在 「正常情况下」的一个
         */
        return new ThreadPoolExecutor(2, 4, 60000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), threadFactory);
    }

}
