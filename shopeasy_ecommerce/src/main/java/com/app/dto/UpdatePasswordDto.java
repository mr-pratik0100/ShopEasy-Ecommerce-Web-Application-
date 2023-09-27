package com.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDto {
	private String email;
	private String oldPassword;
	private String newPassword;
	private String confirmedPassword;
}
