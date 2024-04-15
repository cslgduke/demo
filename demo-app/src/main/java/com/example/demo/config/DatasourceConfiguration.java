package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;

/**
 * @author i565244
 */
//@Configuration
@Slf4j
public class DatasourceConfiguration {

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.hikari")
//    HikariDataSource dataSource(DataSourceProperties properties) {
//        HikariDataSource dataSource = properties.initializeDataSourceBuilder()
//                .type(HikariDataSource.class)
//                .build();
//        if (StringUtils.hasText(properties.getName())) {
//            dataSource.setPoolName(properties.getName());
//        }
//        dataSource.setMetricRegistry(initMetricRegistry(dataSource.getPoolName()));
//        return dataSource;
//    }
//
//    public MetricRegistry initMetricRegistry(String poolName) {
//        MetricRegistry metricRegistry = new MetricRegistry();
//        Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
//                .outputTo(log)
//                .convertRatesTo(TimeUnit.SECONDS)
//                .convertDurationsTo(TimeUnit.MILLISECONDS)
//                .build();
//        reporter.start(30, TimeUnit.SECONDS);//30秒打印一次
//        return metricRegistry;
//    }
}
