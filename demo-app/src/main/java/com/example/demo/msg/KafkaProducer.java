package com.example.demo.msg;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author i565244
 */
@Component
@Slf4j
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void sendMsg(String topic,String key,String value, Map<String,String> headers){
        var recordHeaders = new ArrayList<>();
        headers.keySet().stream().forEach(t -> {
            recordHeaders.add(new RecordHeader(t,String.valueOf(headers.get(t)).getBytes(StandardCharsets.UTF_8)));
        });
        var pRecord = new ProducerRecord(topic,0,key,value,recordHeaders);
        kafkaTemplate.send(pRecord);
        log.info("message send successful：topic:[{}] key:[{}]", topic,key);
    }


    public void sendMessage(String topic,String key,String jsonData) {
        try {
            kafkaTemplate.send(topic, key, jsonData);
        }catch (Exception e) {
            log.error("发送数据出错！！！{}{}", topic, jsonData);
            log.error("发送数据出错",e);
        }

        kafkaTemplate.setProducerListener(new ProducerListener() {
            @Override
            public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
                log.info("向kafka推送数据成功：[{}]", topic);
            }

            @Override
            public void onError(ProducerRecord producerRecord, RecordMetadata recordMetadata, Exception exception) {
                log.error("向kafka推送数据失败：{}{}",topic,key);
            }

        });

    }
}
