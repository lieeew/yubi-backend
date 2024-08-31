package com.leikooo.yubi.config;

import io.github.briqt.spark4j.SparkClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leikooo
 * @Description
 */
@Configuration
@ConfigurationProperties(prefix = "xun-fei.client")
@Data
public class XingHuoConfig {
    private String appId;
    private String apiSecret;
    private String apiKey;

    @Bean
    public SparkClient sparkClient() {
        SparkClient sparkClient = new SparkClient();
        sparkClient.apiKey = apiKey;
        sparkClient.apiSecret = apiSecret;
        sparkClient.appid = appId;
        return sparkClient;
    }
}
