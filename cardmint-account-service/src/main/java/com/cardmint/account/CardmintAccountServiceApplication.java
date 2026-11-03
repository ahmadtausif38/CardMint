package com.cardmint.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {
		"com.cardmint.account",
		"com.cardmint.common"
})
public class CardmintAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardmintAccountServiceApplication.class, args);
	}

}
