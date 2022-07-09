package com.epam.esm.service.config;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan({"com.epam.esm.service"})
@Profile("test")
public class TestServiceConfig {

    @Bean
    @Primary
    public CertificateDao certificateDao() {
        return Mockito.mock(CertificateDao.class);
    }

    @Bean
    @Primary
    public TagDao tagDao() {
        return Mockito.mock(TagDao.class);
    }
}
