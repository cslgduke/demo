package com.cslgduke.demo.camel.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * @author i565244
 */
@Component
@Slf4j
public class PersonProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        //John Doe 23
        var originBoy = exchange.getIn().getBody();
        var newBody = "John Doe 24";
        exchange.getIn().setBody("John Doe 23");
        log.info("process original body:{}, set newbody:{}",originBoy,newBody);
    }
}
