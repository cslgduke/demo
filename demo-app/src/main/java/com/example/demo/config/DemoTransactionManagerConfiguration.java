package com.example.demo.config;

import com.example.demo.transaction.DemoTransaction;
import com.example.demo.transaction.DemoTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author i565244
 */
@Configuration
public class DemoTransactionManagerConfiguration {

    @Bean
    PlatformTransactionManager transactionManager() {
        return new DemoTransactionManager(new DemoTransaction());
    }
}
