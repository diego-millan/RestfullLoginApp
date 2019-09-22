package br.com.loginapplication.login.controller.dto;

import br.com.loginapplication.login.model.Phone;

public class PhoneDTO {
	private String number;
	private int ddd;
	
	public PhoneDTO(Phone phone) {
		this.number = phone.getNumber();
		this.ddd = phone.getDdd();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getDdd() {
		return ddd;
	}

	public void setDdd(int ddd) {
		this.ddd = ddd;
	}
}
