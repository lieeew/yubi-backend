package com.leikooo.yubi.config;

import com.aliyun.broadscope.bailian.sdk.AccessTokenClient;
import lombok.Data;
import org.aopalliance.intercept.Interceptor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leikooo
 * @Description
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private Integer database;

    private String host;

    private String password;

    private String port;
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useReplicatedServers()
                .setDatabase(database)
                .addNodeAddress(String.format("redis://%s:%s", host, port));
        return Redisson.create(config);
    }
}
