package com.cardmint.common.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
//@Configuration
@ConfigurationProperties(prefix = "cardmint.kafka")
public class CardMintKafkaProperties {

    private String bootstrapServers;
    private Producer producer = new Producer();
    private Consumer consumer = new Consumer();
    private Topics topics = new Topics();

    @Data
    public static class Producer {
        private String acks = "all";
        private int retries = 3;
    }

    @Data
    public static class Consumer {

        private String groupId;
        private String autoOffsetReset = "earliest";
    }

    @Data
    public static class Topics {
//        cardmint.transaction.events
//        cardmint.card.events
//        cardmint.account.events
        private String cardEvents;
        private String transactionEvents;
        private String accountEvents;
    }
}