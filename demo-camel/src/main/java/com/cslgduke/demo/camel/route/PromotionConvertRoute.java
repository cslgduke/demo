package com.cslgduke.demo.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author i565244
 */
//@Component
public class PromotionConvertRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        //scheme context-path options
        //Component Endpoint
        from("direct:convert_1001").to("atlasmap:promotion_1001.adm").process(exchange -> {
            log.info("receive msg:{}",exchange.getIn().getBody());
//            var map = exchange.getIn().getBody(Map.class);
//            log.info("receive msg:{}",map);
//            var payload = "";
//            for (Object key: map.keySet()
//                 ) {
//                if(map.get(key) == null){
//                    map.remove(key);
//                }else{
//                    payload = String.valueOf(map.get(key));
//                }
//            }
//            exchange.getIn().setBody(payload);

        }).to("seda:end");

    }

}
