package com.example.demo.config.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author i565244
 */
@Configuration
@AutoConfigureAfter(ConfigurationC.class)
@Slf4j
public class ConfigurationB {

    @Bean
    public OrderTest OrderTestB() {
        log.info("ConfigurationB execute...");
        return  new OrderTest("order-B");
    }
}
