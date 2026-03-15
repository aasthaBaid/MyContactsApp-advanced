package com.contact;

public class Email {
	private String email;

	public Email(String email) {
		if(!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new IllegalArgumentException("Invalid email format");
		}
		this.email = email;
	}


	public String getEmail() {
		return email;
	}

	@Override
	public String toString() { return email; }
}
