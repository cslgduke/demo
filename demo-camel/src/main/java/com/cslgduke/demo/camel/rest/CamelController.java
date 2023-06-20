package com.cslgduke.demo.camel.rest;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = {"/camel/convert"})
@Validated
@RestController
@Slf4j
public class CamelController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @RequestMapping(value = "/promotion/{tenant}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Object process(@PathVariable(name = "tenant") String tenant,@RequestBody final Object promotion) {
        producerTemplate.sendBody("direct:convert_" + tenant, JSON.toJSONString(promotion));
        var msg = consumerTemplate.receiveBody("seda:end_" + tenant, String.class);
        log.info("receive msg:{}", msg);
        return msg;
    }


}
