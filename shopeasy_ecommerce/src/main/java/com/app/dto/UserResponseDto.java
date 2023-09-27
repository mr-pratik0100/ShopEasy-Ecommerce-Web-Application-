package com.app.dto;

import com.app.collections.Avatar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
	private String id;
	private String name;
	private String email;
//	private Avatar avatar;
	private String role;
}
