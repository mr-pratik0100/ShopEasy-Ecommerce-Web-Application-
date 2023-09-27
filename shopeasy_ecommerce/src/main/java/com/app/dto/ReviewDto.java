package com.app.dto;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class ReviewDto {
	private String productId;
	private String comment;
	private int rating;
}
