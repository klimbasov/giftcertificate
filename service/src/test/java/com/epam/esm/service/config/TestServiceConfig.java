package com.epam.esm.service.config;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan("com.epam.esm.service")
@Profile("test")
public class TestServiceConfig {

    @Bean
    public CertificateDao certificateDao() {
        return Mockito.mock(CertificateDaoImpl.class);
    }

    @Bean
    public TagDao tagDao() {
        return Mockito.mock(TagDaoImpl.class);
    }
}
