package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author i565244
 */

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("javaboy").password("{bcrypt}$2a$10$Sb1gAUH4wwazfNiqflKZve4Ubh.spJcxgHG8Cp29DeGya5zsHENqi").roles("admin", "aaa", "bbb").build());
//        manager.createUser(User.withUsername("sang").password("{noop}123").roles("admin").build());
//        manager.createUser(User.withUsername("江南一点雨").password("{MD5}{Wucj/L8wMTMzFi3oBKWsETNeXbMFaHZW9vCK9mahMHc=}4d43db282b36d7f0421498fdc693f2a2").roles("user", "aaa", "bbb").build());
//        return manager;
//    }

//    @Bean("DppProxyServiceFilterChain")
//    public SecurityFilterChain appFilterChain(HttpSecurity http) throws Exception {
//
//        return http
//                .securityMatcher("/actuator/**","/logger/**")
//                .authorizeHttpRequests(r -> r.anyRequest().permitAll())
//                .build();
//    }

}
