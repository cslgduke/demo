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

    @KafkaListener(topics = "dpp_datasubject_deletion", groupId = "demo-app")
    public void consumerMsg(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("receive msg:{}",record);
        ack.acknowledge();
    }
}
