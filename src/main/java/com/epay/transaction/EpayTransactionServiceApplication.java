package com.epay.transaction;

import com.epay.transaction.config.audit.SpringSecurityAuditorAware;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.sbi.epay.encryptdecrypt.service.DecryptionService;
import com.sbi.epay.encryptdecrypt.service.EncryptionService;
import com.sbi.epay.hazelcast.service.HazelcastService;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Copyright (c) [2024] [State Bank of India]
 * All rights reserved.
 *
 * Author:@author Shilpa Kothre
 */


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"org.springframework.boot.context.embedded.tomcat", "com.epay.transaction", "com.sbi.epay.authentication"})
@EnableJpaRepositories(basePackages = "com.epay.transaction")
@EntityScan(basePackages = "com.epay.transaction")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableScheduling
public class EpayTransactionServiceApplication implements WebMvcConfigurer {

    private static final String CLUSTER_NAME = "Epay_Transaction_service";

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

    //TODO: This is temporary  provision of hazelcast configuration. Once dev-cluster is ready, cluster name will be set accordingly.
    @Bean
    public HazelcastInstance buidHazelcastInstance() {
        Config config = new Config();
        config.setClusterName(CLUSTER_NAME);
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public HazelcastService buidHazelcastService() {
        return new HazelcastService();
    }

    @Bean
    public EncryptionService buildEncryptionService() {
        return new EncryptionService();
    }

    @Bean
    public DecryptionService buildDecryptionService() {
        return new DecryptionService();
    }
}