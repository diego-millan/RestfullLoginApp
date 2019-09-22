package br.com.loginapplication.login.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.loginapplication.login.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class CustomTokenService {
	
	@Value("${jwt.expiration.time}")
	private String expiration;
	
	@Value("${jwt.secret.key}")
	private String secretKey;
	
	@Value("${jwt.builder.issuer}")
	private String builderIssuer;
	
	public String createToken(Authentication authentication) {
		
		User user = (User) authentication.getPrincipal();
		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder().setIssuer(builderIssuer)
				.setSubject(user.getId().toString())
				.setIssuedAt(currentDate)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public boolean isValidToken(String token) {
		
		boolean isValid = false;
		
		try {
			Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);
			isValid = true;
		} catch (Exception e) {
		}
		
		return isValid;
	}

	public Long getUserId(String token) {
		Jws<Claims> JwtsClaims =Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);
		Claims claims = JwtsClaims.getBody();
		return Long.parseLong(claims.getSubject());
	}
}
