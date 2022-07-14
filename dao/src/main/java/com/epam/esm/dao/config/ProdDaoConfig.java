package com.epam.esm.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan("com.epam.esm.dao")
@Profile("prod")
public class ProdDaoConfig {
}
