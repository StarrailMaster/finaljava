package com.intelligent.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/customer_recommend/**").permitAll() // 需要认证的路径
                        .requestMatchers("/api/merchant_recommend/**").permitAll() // 需要认证的路径
                        .requestMatchers("/api/login/**").permitAll() // 需要认证的路径
                        .requestMatchers("/api/register/**").permitAll() // 需要认证的路径
                        .requestMatchers("/api/search/**").permitAll() // 需要认证的路径
                        .requestMatchers("/api/recommendation/**").permitAll() // 需要认证的路径
                        .requestMatchers("/api/behavior/**").permitAll() // 需要认证的路径
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 根据需要创建会话
                )
                .cors().and().csrf().disable(); // 允许跨域并禁用 CSRF

        return http.build();
    }
}
