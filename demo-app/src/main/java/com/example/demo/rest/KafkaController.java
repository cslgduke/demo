package com.example.demo.rest;

import com.example.demo.msg.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author i565244
 */
@RequestMapping(value = {"/kafka"})
@Validated
@RestController
@Slf4j
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping("/sendMsgs")
    public Object sendMsgs() {
        for (int i = 0; i < 10; i++) {
            sendKafkaMsg("demo-topic");
            sendKafkaMsg("demo-topic2");
        }
        return "success";
    }


    @PostMapping("/sendMsg/{topic}/{partition}")
    public Object sendMsg(@PathVariable String topic, @PathVariable String partition) {
        sendKafkaMsg(topic,Integer.valueOf(partition));
        return "success";
    }


    private void sendKafkaMsg(String topic){
        var key = generateUUID();
        var value = "test kafka msg:" + key;
//        kafkaProducer.sendMsg(topic,key, value);
        kafkaProducer.sendMsg(topic, value);
    }


    private void sendKafkaMsg(String topic,Integer partition){
        var key = generateUUID();
        var value = "test kafka msg:" + key;
        var headers = new HashMap<String, String>();
        headers.put("X-Partition", partition.toString());
        headers.put("X-Message-ID", key);
        kafkaProducer.sendMsg(topic, partition,key, value, headers);
    }

    public String generateUUID() {
        return StringUtils.upperCase(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
    }

}
