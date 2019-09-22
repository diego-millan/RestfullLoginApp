package br.com.loginapplication.login.controller.form;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.loginapplication.login.model.Phone;
import br.com.loginapplication.login.model.User;

public class UserForm {
	
	@NotNull @NotEmpty @Length(min = 4, max = 30)
	private String name;
	
	@NotNull @NotEmpty @Length(min = 8, max = 40)
	private String email;
	
	@NotNull @NotEmpty @Length(min = 6, max = 30) 
	private String password;
	
	private List<Phone> phones;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	public User convert() {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);
		user.setPhones(phones);
		return user;
	}
	public UsernamePasswordAuthenticationToken createAuthenticationToken() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
