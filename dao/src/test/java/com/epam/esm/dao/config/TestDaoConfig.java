package com.epam.esm.dao.config;

import com.epam.esm.dao.util.scriptrunner.ScriptRunner;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm.dao")
@Profile("test")
public class TestDaoConfig {

    @PostConstruct
    public void init() throws Exception {
        ResourceLoader resourceLoader = new AnnotationConfigApplicationContext(TestDaoConfig.class);
        HikariConfig hikariConfig = new HikariConfig("/db_scriptrunner.properties");
        try (HikariDataSource dataSource = new HikariDataSource(hikariConfig);
             ScriptRunner scriptRunner = new ScriptRunner(dataSource, resourceLoader)) {
            scriptRunner.run("/sql/create_database.sql");
        }
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(new HikariConfig("/db_test.properties"));
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
