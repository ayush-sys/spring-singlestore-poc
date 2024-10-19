package com.singlestore.singlestore_application.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/** The database config. */

@Data
@Configuration
public class DbConfig {

    /* The database config bean. */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig singlestoreHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Primary
    public DataSource getS2DataSource() {
        return new HikariDataSource(singlestoreHikariConfig());
    }
}
