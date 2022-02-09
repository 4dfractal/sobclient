package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HeartbeatClientRun {
	public static void main(String[] args) {
		SpringApplication.run(HeartbeatClientConfig.class, args);
	}
}
