package com.example.kevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KeventsApplication {

	public static void main(String[] args) {
		        io.github.cdimascio.dotenv.Dotenv dotenv = io.github.cdimascio.dotenv.Dotenv.configure().directory("./").load();
        SpringApplication.run(KeventsApplication.class, args);
	}

}
