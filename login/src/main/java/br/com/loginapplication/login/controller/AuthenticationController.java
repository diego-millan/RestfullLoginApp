package br.com.loginapplication.login.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.loginapplication.login.config.security.CustomTokenService;
import br.com.loginapplication.login.controller.dto.TokenDTO;
import br.com.loginapplication.login.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private static final String TOKEN_TYPE_BEARER = "Bearer";

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomTokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> authenticate(@RequestBody @Valid LoginForm form) {
		
		ResponseEntity<TokenDTO> result;
		UsernamePasswordAuthenticationToken loginData = form.createAuthenticationToken();
		
		try {
			
			Authentication authentication = authenticationManager.authenticate(loginData);
			String generatedToken = tokenService.createToken(authentication);
			result = ResponseEntity.ok(new TokenDTO(generatedToken, TOKEN_TYPE_BEARER));
			
		} catch (AuthenticationException e) {
			result = ResponseEntity.badRequest().build();
		}
		
		return result;
	}
}
