package br.com.loginapplication.login.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.loginapplication.login.model.Phone;
import br.com.loginapplication.login.model.User;

public class UserInfoDTO {
	private String username;
	private String email;
	private List<PhoneDTO> phones;
	private Long id;
	private LocalDateTime creationDate;
	private LocalDateTime modified;
	private LocalDateTime last_login;
	private String token;
	
	public UserInfoDTO(User user, TokenDTO tokenDTO) {
		this.username = user.getName();
		this.email = user.getEmail();
		this.id = user.getId();
		this.last_login = user.getLast_login();
		this.modified = user.getModified();
		this.creationDate = user.getCreationDate();
		
		this.phones = new ArrayList<PhoneDTO>();
		for (Phone phone : user.getPhones()) {
			phones.add(new PhoneDTO(phone));
		}
		
		if (tokenDTO != null) {
			this.token = tokenDTO.getToken();
		}
	}

	public UsernamePasswordAuthenticationToken createAuthenticationToken(String password) {
		return new UsernamePasswordAuthenticationToken(email, password);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<PhoneDTO> getPhones() {
		return phones;
	}

	public void setPhones(List<PhoneDTO> phones) {
		this.phones = phones;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	public LocalDateTime getLast_login() {
		return last_login;
	}

	public void setLast_login(LocalDateTime last_login) {
		this.last_login = last_login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
