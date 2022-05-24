package com.epam.esm.dao.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm.dao")
@Profile("dev")
public class DaoConfiguration {

    @Bean
    public DataSource dataSource(){
        return new HikariDataSource(new HikariConfig("/db_test.properties"));
    }
}
