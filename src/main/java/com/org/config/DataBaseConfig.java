package com.org.config;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataBaseConfig implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(DataBaseConfig.class);

    private Environment env;

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() throws Exception {

        String maxPoolSizeStr = this.env.getProperty("jdbc.maxPoolSize");
        String idleTimeoutStr = this.env.getProperty("jdbc.idleTimeout");
        String userName = this.env.getProperty("jdbc.username");
        String password = this.env.getProperty("jdbc.password");
        String url = this.env.getProperty("jdbc.url");

        log.info("---------->jdbcurl={}", url);
        log.info("---------->userName={}", userName);
        log.info("---------->maxPoolSize={}", maxPoolSizeStr);
        log.info("---------->idleTimeOut={}", idleTimeoutStr);

        int maxPoolSize = Integer.valueOf(maxPoolSizeStr);
        int idleTimeout = Integer.valueOf(idleTimeoutStr);

        HikariConfig config = new HikariConfig();
        config.setPoolName("SpringHikariEasyEmail");
        config.setConnectionTestQuery("SELECT 1");
        config.setDriverClassName("com.mysql.jdbc.Driver");

        config.setMaximumPoolSize(maxPoolSize);
        config.setIdleTimeout(idleTimeout);

        config.setUsername(userName);
        config.setPassword(password);
        config.setJdbcUrl(url);

        config.addDataSourceProperty("useUnicode", "true");
        config.addDataSourceProperty("characterEncoding", StandardCharsets.UTF_8.name());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        return new HikariDataSource(config);

    }

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
    }

}