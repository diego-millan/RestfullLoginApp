package br.com.loginapplication.login.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.loginapplication.login.controller.dto.UserDTO;
import br.com.loginapplication.login.controller.dto.UserInfoDTO;
import br.com.loginapplication.login.controller.form.UserForm;
import br.com.loginapplication.login.model.User;
import br.com.loginapplication.login.repository.UserRepository;

@RestController
@RequestMapping("/login")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
    public List<UserDTO> listUsers(String email) {
		
		List<UserDTO> userInfoDTOList = new ArrayList<UserDTO>();
		
		if(email == null) {
			List<User> users = userRepository.findAll();
			
			for (User user : users) {
				userInfoDTOList.add(new UserDTO(user));
			}
		} else {
			Optional<User> user = userRepository.findByEmail(email);
			userInfoDTOList.add(new UserDTO(user.get()));
		}
		
        return userInfoDTOList;
    }
	
	@PostMapping
	public ResponseEntity<UserDTO> CreateUser(@RequestBody  @Valid UserForm userForm, 
			UriComponentsBuilder uriBuilder) {
		
		User user = userForm.convert();
		userRepository.save(user);

		URI uri = uriBuilder.path("/login/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(new UserDTO(user));
	}
	
//	@GetMapping("/{username}")
//	public ResponseEntity<UserInfoDTO> displayUserInfo(@PathVariable String username) {
//		
//		ResponseEntity<UserInfoDTO> response;
//		
//		Optional<User> user = userRepository.findByEmail(username);
//		
//		if (user == null) {
//			response = ResponseEntity.notFound().build();
//		} else {
//			response = ResponseEntity.ok(new UserInfoDTO(user));
//		}
//		
//		return response;
//	}
}
