package com.epam.esm.dao.config;

import com.epam.esm.dao.util.scriptrunner.ScriptRunner;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan("com.epam.esm.dao")
@Profile("dev")
public class DevDaoConfig {

    @PostConstruct
    public void init() throws SQLException {
        ResourceLoader resourceLoader = new AnnotationConfigApplicationContext(DevDaoConfig.class);
        HikariConfig hikariConfig = new HikariConfig("/db_scriptrunner.properties");
        try (HikariDataSource dataSource = new HikariDataSource(hikariConfig);
             ScriptRunner scriptRunner = new ScriptRunner(dataSource, resourceLoader)) {
            scriptRunner.run("/dev/scripts/create_database.sql");
        }
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(new HikariConfig("/dev/db.properties"));
    }
}
