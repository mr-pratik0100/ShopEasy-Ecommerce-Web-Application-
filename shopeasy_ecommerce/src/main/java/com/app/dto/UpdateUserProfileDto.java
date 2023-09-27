package com.app.dto;

import com.app.collections.Avatar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserProfileDto {
	private String name;
	private String email;
//	private Avatar avatar;
}
