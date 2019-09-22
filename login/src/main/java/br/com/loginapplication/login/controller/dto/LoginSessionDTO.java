package br.com.loginapplication.login.controller.dto;

import java.time.LocalDateTime;

import br.com.loginapplication.login.model.LoginSession;

public class LoginSessionDTO {
	
	private LocalDateTime last_login;
	private String token;
	
	public LoginSessionDTO(LoginSession loginSession) {
		this.last_login = loginSession.getLastLogin();
		this.token = loginSession.getToken();
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
