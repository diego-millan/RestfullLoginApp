package br.com.loginapplication.login.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.loginapplication.login.config.validate.dto.ErrorMessageDTO;
import br.com.loginapplication.login.controller.dto.TokenDTO;
import br.com.loginapplication.login.controller.dto.UserInfoDTO;
import br.com.loginapplication.login.controller.form.LoginForm;
import br.com.loginapplication.login.controller.form.UserForm;
import br.com.loginapplication.login.model.User;
import br.com.loginapplication.login.repository.UserRepository;

@RestController
@RequestMapping("/login")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private AuthenticationController authenticationController;
	
	@GetMapping
    public ResponseEntity<?> listUsers(Long id) {
		
		ResponseEntity<?> responseEntity;
		
		if(id == null) {
			ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO("Não autorizado.");
			responseEntity = ResponseEntity.badRequest().body(errorMessageDTO);
		} else {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				responseEntity = displayUserInfo(user.get().getEmail(), null);
			}
			else {
				ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO("Usuário e/ou senha inválidos.");
				responseEntity = ResponseEntity.badRequest().body(errorMessageDTO);
			}
		}
		
        return responseEntity;
    }
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody  @Valid UserForm userForm, 
			UriComponentsBuilder uriBuilder) {
		
		ResponseEntity<?> responseEntity;
		
		User user = userForm.convert();
		
		if (usernameAlreadyExists(user)) {
			
			ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO("E-mail já existente.");
			responseEntity = ResponseEntity.badRequest().body(errorMessageDTO);
			
		} else {
			
			user.setPassword(passwordEncoder.encode(userForm.getPassword()));
			// System.out.println("User password: " + user.getPassword());
			userRepository.save(user);

			ResponseEntity<UserInfoDTO> userInfoDTO = authenticateCreatedUser(userForm);
			
			URI uri = uriBuilder.path("/login/{id}").buildAndExpand(user.getId()).toUri();
			responseEntity = ResponseEntity.created(uri).body(userInfoDTO);
		}
		
		return responseEntity;
	}

	private ResponseEntity<UserInfoDTO> authenticateCreatedUser(UserForm userForm) {
		
		LoginForm loginForm = new LoginForm();
		loginForm.setEmail(userForm.getEmail());
		loginForm.setPassword(userForm.getPassword());
		
		ResponseEntity<UserInfoDTO> userInfoDTO = 
				(ResponseEntity<UserInfoDTO>) authenticationController.authenticate(loginForm);
		
		return userInfoDTO;
	}

	private boolean usernameAlreadyExists(User user) {

		Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
		
		return foundUser.isPresent();
	}
	
	public ResponseEntity<UserInfoDTO> displayUserInfo(String email, TokenDTO tokenDTO) {
		
		ResponseEntity<UserInfoDTO> response;
		
		Optional<User> user = userRepository.findByEmail(email);
		
		if (user.isPresent()) {
			response = ResponseEntity.ok(new UserInfoDTO(user.get(), tokenDTO));
		} else {
			response = ResponseEntity.notFound().build();
		}
		
		return response;
	}
}
