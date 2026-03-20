package com.cardmint.common.kafka.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CardMintKafkaProperties.class)
@RequiredArgsConstructor
public class KafkaAutoConfiguration {
}
