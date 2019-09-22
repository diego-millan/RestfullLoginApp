package br.com.loginapplication.login.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.loginapplication.login.model.Phone;
import br.com.loginapplication.login.model.User;

public class UserInfoDTO {
	private String username;
	private String email;
	private List<PhoneDTO> phones;
	private Long id;
	private LocalDateTime creationDate = LocalDateTime.now();
	private LocalDateTime modified;
	private LoginSessionDTO loginSessionDTO;
	
	public UserInfoDTO(User user) {
		this.username = user.getName();
		this.email = user.getEmail();
		this.id = user.getId();
		this.loginSessionDTO = new LoginSessionDTO(user.getLoginSession());
		
		this.phones = new ArrayList<PhoneDTO>();
		for (Phone phone : user.getPhones()) {
			phones.add(new PhoneDTO(phone));
		}
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

	public LoginSessionDTO getLoginSessionDTO() {
		return loginSessionDTO;
	}

	public void setLoginSessionDTO(LoginSessionDTO loginSessionDTO) {
		this.loginSessionDTO = loginSessionDTO;
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
}
