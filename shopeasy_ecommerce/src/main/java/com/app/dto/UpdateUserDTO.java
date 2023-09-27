package com.app.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {

	private String name;
	private String oldPassword;
	private String newPassword;
}
