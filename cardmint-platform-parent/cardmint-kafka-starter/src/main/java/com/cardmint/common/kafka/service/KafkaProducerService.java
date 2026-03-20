package com.cardmint.common.kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, String key, Object payload) {

        kafkaTemplate.send(topic, key, payload)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("[Kafka send failed for topic {}]", topic, ex);
                    } else {
                        RecordMetadata meta = result.getRecordMetadata();
                        log.info(
                                "[Message sent to Kafka topic = {}, partition= {}, offset= {}]",
                                meta.topic(),
                                meta.partition(),
                                meta.offset()
                        );
                    }
                });
    }
}
