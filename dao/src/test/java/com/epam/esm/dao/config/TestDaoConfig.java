package com.epam.esm.dao.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.epam.esm.dao")
@Profile("test")
@EntityScan("com.epam.esm.dao")
@EnableTransactionManagement
public class TestDaoConfig {
}
