package com.example.kevents.auth.jwt;

import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;

@Component
@Getter
public class JwtProperties {

	private final String jwtUser;
	private final String jwtSecret;

	public JwtProperties() {
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();
		this.jwtUser = dotenv.get("JWT_USER");
		this.jwtSecret = dotenv.get("JWT_SECRET_KEY");
	}

}
