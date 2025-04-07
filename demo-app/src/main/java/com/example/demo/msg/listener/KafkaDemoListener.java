package com.example.demo.msg.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

/**
 * @author i565244
 */
@Service
@Slf4j
public class KafkaDemoListener {

    @KafkaListener(topics = "demo-topic", groupId = "demo-app")
    public void consumerMsg(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("receive msg:{},topic:{},partition:{},offset:{}",record.value(),record.topic(),record.partition(),record.offset());
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(record.offset() <= 25){
            throw new RuntimeException("Consume msg fail");
        }
        ack.acknowledge();
    }

    @KafkaListener(topics = "demo-topic2", groupId = "demo-app")
    public void consumerMsg2(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("receive msg:{},topic:{},partition:{}",record.value(),record.topic(),record.partition());
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ack.acknowledge();
    }
}
