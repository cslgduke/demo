package com.cslgduke.demo.camel.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author i565244
 */
@Slf4j
@Component
public class CamelConfigInit implements CommandLineRunner {

    @Autowired
    private CamelContext context;

    @Override
    public void run(String... args) throws Exception {

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:convert_1001").to("atlasmap:file:/Users/i565244/camel/promotion_1001.adm").to("seda:end_1001");
            }
        });

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:convert_1002").to("atlasmap:promotion_1002.adm").to("seda:end_1002");
            }
        });

    }
}
