package com.app.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.app.collections.Image;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDto {
	@NotBlank(message="Product name can't blank")
	private String prodName;
	private String description;
	@NotBlank(message="plz select the price")
	private double price;
	private List<Image> image;
	@NotBlank(message="plz select the category")
	private String Category;
	private int stock;
	private int rating;
	
	private LocalDate createdAt;
}
