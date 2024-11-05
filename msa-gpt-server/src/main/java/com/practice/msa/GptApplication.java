package com.practice.msa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GptApplication {

	public static void main(String[] args) {
		SpringApplication.run(GptApplication.class, args);
	}

}
