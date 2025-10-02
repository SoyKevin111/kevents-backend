package com.example.kevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class KeventsApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		SpringApplication.run(KeventsApplication.class, args);
	}

}
