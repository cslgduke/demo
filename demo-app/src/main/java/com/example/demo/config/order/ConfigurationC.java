package com.example.demo.config.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author i565244
 */
@Configuration
@Slf4j
public class ConfigurationC {

    @Bean
    public OrderTest OrderTestC() {
        log.info("ConfigurationC execute...");
        return  new OrderTest("order-C");
    }
}
