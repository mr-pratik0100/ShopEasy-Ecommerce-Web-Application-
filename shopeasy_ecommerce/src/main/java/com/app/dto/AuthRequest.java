package com.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthRequest {
	@NotBlank(message = "Email can't be blank")
	private String email;
	@NotBlank(message = "Email can't be blank")
	@Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must be atleast 8 charcters contain at least one upper case, one lower case, at least one digit  at least one char within a set of special chars (@#%$^)")
	private String password;
}


