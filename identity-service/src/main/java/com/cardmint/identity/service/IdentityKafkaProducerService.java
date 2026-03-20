package com.cardmint.identity.service;

import com.cardmint.common.kafka.config.CardMintKafkaProperties;
import com.cardmint.common.kafka.service.KafkaProducerService;
import com.cardmint.identity.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service("identityKafkaProducerService")
public class IdentityKafkaProducerService {

    private final KafkaProducerService kafkaProducerService;
    private final CardMintKafkaProperties kafkaProperties;

    public void publishKafkaEvent(UserEntity payload){

        try {
            // Publish CUSTOMER_REGISTERED event
            Map<String, Object> event = new HashMap<>();
            event.put("eventType", "CUSTOMER_REGISTERED");
            event.put("customerId", payload.getUserId());
            event.put("fullName", payload.getFullName());
            event.put("panHash", payload.getPanHash());
            event.put("timestamp", System.currentTimeMillis());

            kafkaProducerService.send(kafkaProperties.getTopics().getAccountEvents(), payload.getUserId(), event);
            log.info("Message published to kafka");
        } catch (Exception e) {
            log.error("Error occured while publishing to kafka {}",e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

}
