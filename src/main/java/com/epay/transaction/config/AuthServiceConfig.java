package com.epay.transaction.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthServiceConfig {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${security.whitelist.url}")
    private String[] whiteListUrls;

//    @Bean
//    public AuthenticationService constructAuthServiceImpl() {
//        AppConfig apiConfig = new AppConfig();
//        apiConfig.setSecretKey(secretKey);
//        apiConfig.setWhiteListUrls(whiteListUrls);
//        JwtService jwtService = new JwtService(apiConfig);
//        return new AuthenticationService(jwtService);
//    }
}