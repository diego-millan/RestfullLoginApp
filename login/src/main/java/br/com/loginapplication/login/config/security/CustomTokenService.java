package br.com.loginapplication.login.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.loginapplication.login.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class CustomTokenService {
	
	@Value("${jwt.expiration.time}")
	private String expiration;
	
	@Value("${jwt.secret.key}")
	private String secretKey;
	
	public String createToken(Authentication authentication) {
		
		User user = (User) authentication.getPrincipal();
		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder().setIssuer("Login Authentication")
				.setSubject(user.getId().toString())
				.setIssuedAt(currentDate)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
}
