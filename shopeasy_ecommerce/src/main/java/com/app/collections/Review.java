package com.app.collections;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Review {
	private String id;
	private String user;
	private String name;
	private int rating;
	private String comment;
}
