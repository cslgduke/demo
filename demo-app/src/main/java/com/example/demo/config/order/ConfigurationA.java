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
@AutoConfigureAfter(ConfigurationB.class)
public class ConfigurationA {
    /**
     *    spring只对spring.factory文件下的配置类进行排序
     *
     */

    @Bean
    public OrderTest OrderTestA() {
        log.info("ConfigurationA execute...");
        return  new OrderTest("order-A");
    }
}
