package com.epay.transaction;

import com.epay.transaction.config.audit.SpringSecurityAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Copyright (c) [2024] [State Bank of India]
 * All rights reserved.
 *
 * Author:@author Shilpa Kothre
 */


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@ComponentScan(basePackages = {
        "org.springframework.boot.context.embedded.tomcat",
        "com.epay.transaction"
})
@EnableJpaRepositories(basePackages = "com.epay.transaction")
@EntityScan(basePackages = "com.epay.transaction")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EpayTransactionServiceApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(EpayTransactionServiceApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new SpringSecurityAuditorAware();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

    }


}
