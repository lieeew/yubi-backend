package com.leikooo.yubi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ArrayBlockingQueue;
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
        /*
            1、corePoolSize 这个是最重要的一个参数，是在 「正常情况下」的一个员工数目
            2、maximumPoolSize 这个是最大的员工数目
            3、keepAliveTime 这个是当线程数大于 corePoolSize 的时候，多余的线程空闲多长时间之后会被销毁
            4、unit 这个是 keepAliveTime 的单位
            5、workQueue 这个是用来存放任务的队列，当线程数大于 corePoolSize 的时候，如果队列满了，就会把任务放到队列中，直到队列有空闲的线程
         */
        return new ThreadPoolExecutor(2, 4, 60000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
    }

}
