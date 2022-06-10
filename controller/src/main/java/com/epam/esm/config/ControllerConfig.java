package com.epam.esm.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;


@Configuration
@EnableWebMvc
@ComponentScan({"com.epam.esm"})
@Profile("dev|prod")
public class ControllerConfig implements WebMvcConfigurer {


    @Bean
    @Primary
    public MessageSource messageResource() {
        Locale.setDefault(Locale.US);
        ResourceBundleMessageSource messageResource = new ResourceBundleMessageSource();
        messageResource.setBasename("language/lang");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }
}
