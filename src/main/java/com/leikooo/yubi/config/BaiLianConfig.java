package com.leikooo.yubi.config;

import com.aliyun.broadscope.bailian.sdk.AccessTokenClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leikooo
 * @Description
 */
@Configuration
@ConfigurationProperties(prefix = "bai-lian")
@Data
public class BaiLianConfig {
    private String agentKey;
    private String accessKeySecret;
    private String accessKeyId;
    private AccessTokenClient accessTokenClient;
    @Bean
    public AccessTokenClient accessTokenClient() {
        return new AccessTokenClient(accessKeyId, accessKeySecret, agentKey);
    }
}
