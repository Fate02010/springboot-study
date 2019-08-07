package com.fate.spring.kafkademo.components;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * kafka 接收器
 * 
 * @author maijinchao
 *
 */
@Slf4j
@Component
public class KafkaReceiver {

    @KafkaListener(topics = "test")
    public void listen(@Payload String message) {
        log.info(Thread.currentThread().getName() + "----------" + Thread.currentThread().getId());
        log.info("received message='{}'", message);
    }

}
