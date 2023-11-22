package com.leikooo.yubi.config;

import com.rabbitmq.client.ConnectionFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leikooo
 * @Description
 */
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Data
public class RabbitMQClientConfig {

    private String host;

    private Integer port;

    private String username;

    private String password;

}
