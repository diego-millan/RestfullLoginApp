package br.com.loginapplication.login.config.validate;

public class InvalidFormDTO {
	private String field;
	private String error;
	
	public String getField() {
		return field;
	}

	public String getError() {
		return error;
	}

	public InvalidFormDTO(String field, String error) {
		this.field = field;
		this.error = error;
	}
}
