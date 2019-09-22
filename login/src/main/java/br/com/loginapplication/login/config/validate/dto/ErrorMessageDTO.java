package br.com.loginapplication.login.config.validate.dto;

public class ErrorMessageDTO {
	
	private String message;
	
	public ErrorMessageDTO (String message) {
		this.setMessage(message);
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
