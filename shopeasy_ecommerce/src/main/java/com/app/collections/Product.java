package com.app.collections;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection = "product")
public class Product {

	@Id
	private String id;
	private String prodName;
	private String description;
	private double price;
	private int rating;
	private List<Image> image;
	private String category;
	private int stock;
	private int noOfReviews;
	private List<Review> review = new ArrayList<Review>();
	private String user;
	private LocalDate createdAt = LocalDate.now();

	public Product() {

	}

//	public Product(String prodName, String description, double price, int rating, ArrayList<Image> image,
//			String category, int stock, int noOfReviews, ArrayList<Reviews> review, LocalDate createdAt) {
//		super();
//		this.prodName = prodName;
//		this.description = description;
//		this.price = price;
//		this.rating = rating;
//		this.image = image;
//		this.category = category;
//		this.stock = stock;
//		this.noOfReviews = noOfReviews;
//		this.review = review;
//		this.createdAt = createdAt;
//	}
	public Product(String prodName, String description, double price, int rating, List<Image> image, String category,
			int stock, int noOfReviews, List<Review> review, LocalDate createdAt) {
		super();
		this.prodName = prodName;
		this.description = description;
		this.price = price;
		this.rating = rating;
		this.image = image;
		this.category = category;
		this.stock = stock;
		this.noOfReviews = noOfReviews;
		this.review = review;
		this.createdAt = createdAt;
	}

}
