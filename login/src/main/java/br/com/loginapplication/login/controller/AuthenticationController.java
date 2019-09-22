package br.com.loginapplication.login.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import br.com.loginapplication.login.config.validate.dto.ErrorMessageDTO;
import br.com.loginapplication.login.controller.dto.TokenDTO;
import br.com.loginapplication.login.controller.form.LoginForm;
import br.com.loginapplication.login.model.User;
import br.com.loginapplication.login.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private static final String TOKEN_TYPE_BEARER = "Bearer";

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomTokenService tokenService;
	
	@PostMapping
	public ResponseEntity<?> authenticate(@RequestBody @Valid LoginForm form) {
		
		ResponseEntity<?> result;
		
		UsernamePasswordAuthenticationToken loginData = form.createAuthenticationToken();
		
		try {
			
			Authentication authentication = authenticationManager.authenticate(loginData);
			String generatedToken = tokenService.createToken(authentication);
			
			result = userController.displayUserInfo(
					form.getEmail(), new TokenDTO(generatedToken, TOKEN_TYPE_BEARER));
			
			
		} catch (AuthenticationException e) {
			
			ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO("Usuário e/ou senha inválidos.");
			Optional<User> user = userRepository.findByEmail(form.getEmail());
			
			if (user.isPresent()) {
				result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessageDTO);
			}
			else {
				result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageDTO);
			}
		}
		
		return result;
	}
}
