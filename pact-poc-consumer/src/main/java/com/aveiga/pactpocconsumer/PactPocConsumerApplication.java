package com.aveiga.pactpocconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PactPocConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PactPocConsumerApplication.class, args);
	}

}
