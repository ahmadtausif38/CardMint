package com.cardmint.cardissue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cardmint.cardissue", "com.cardmint.common"})
public class CardmintCardissueApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardmintCardissueApplication.class, args);
	}

}
