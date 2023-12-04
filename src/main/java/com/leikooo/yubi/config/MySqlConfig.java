package com.leikooo.yubi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author leikooo
 * @Description
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class MySqlConfig {

    private String url;

    private String username;

    private String password;

    @Bean
    public Connection connection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
