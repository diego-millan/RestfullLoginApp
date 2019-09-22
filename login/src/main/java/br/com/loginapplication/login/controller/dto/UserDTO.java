package br.com.loginapplication.login.controller.dto;

import br.com.loginapplication.login.model.User;

public class UserDTO {
	
	private String username;
	private String email;
	
	public UserDTO(User user) {
		this.setUsername(user.getName());
		this.setEmail(user.getEmail());
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
}
