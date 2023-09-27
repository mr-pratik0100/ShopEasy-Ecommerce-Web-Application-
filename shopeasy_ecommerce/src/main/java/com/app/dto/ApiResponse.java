package com.app.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ApiResponse {
	private String message;
	private LocalDateTime timeStamp;

	public ApiResponse(String msg) {
		this.message = msg;
		this.timeStamp = LocalDateTime.now();
	}
}
