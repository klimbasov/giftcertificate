package com.epam.esm.dao.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm.dao")
@Profile("dev")
@PropertySource(value = {"classpath:application.properties"})
public class DevDaoConfig {

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(new HikariConfig("/dev/db.properties"));
    }
}
