package com.app.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.app.collections.Product;
import com.app.collections.Review;
import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.ProductNotFoundException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ALlProductandCountDTO;
import com.app.dto.ProductDto;
import com.app.dto.ReviewDto;

public interface ProductService {

	public String createProduct(ProductDto productdto, HttpSession session) throws ErrorHandler;

	public String deleteProduct(String id, HttpSession session) throws ErrorHandler;

	ALlProductandCountDTO getAllProduct(String keyword, String category, Double lt, Double lte, Double gt, Double gte,
			Integer page) throws ErrorHandler;

	public List<Product> getAllProductsAdmin(HttpSession session) throws ErrorHandler;

	Product getProductById(String id) throws ResourceNotFoundException;

	public Product updateProduct(String id, ProductDto productDto);

	public String createProductReview(ReviewDto reviewDto, HttpSession session) throws ProductNotFoundException;
	
	public List<Review> getProductReviews(String id) throws ProductNotFoundException;
	
	public String deleteReview(String productId, String id,HttpSession session) throws ProductNotFoundException, ErrorHandler;
}
