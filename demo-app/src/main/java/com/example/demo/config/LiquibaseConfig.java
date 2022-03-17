package com.example.demo.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author i565244
 */
@Configuration
public class LiquibaseConfig {

//    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) throws Exception{
        SpringLiquibase liquibase=new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/master.xml");
        liquibase.setContexts("application");
        return liquibase;
    }

}
