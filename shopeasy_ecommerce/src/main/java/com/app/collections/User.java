package com.app.collections;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@Document(collection = "user")
public class User {
	@Id
	private String id;
	private String name;
	@Field("email")
	private String email;
	@Field("password")
	private String password;
//	private Avatar avatar;
	@Field("role")
	private String role = "user";
	private String resetPasswordToken = "";
	private LocalDateTime resetPasswordExpire = LocalDateTime.now();

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String id, String name, String email, String password, String role,
			String resetPasswordToken, LocalDateTime resetPasswordExpire) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
//		this.avatar = avatar;
		this.role = "user";
		this.resetPasswordToken=resetPasswordToken;
		this.resetPasswordExpire=resetPasswordExpire;

	}
}
