package com.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.collections.Product;
import com.app.collections.Review;
import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.ProductNotFoundException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ALlProductandCountDTO;
import com.app.dto.ProductDto;
import com.app.dto.ReviewDto;
import com.app.service.ProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
	@Autowired
	private ProductService productservice;

	@PostMapping("/admin/create")
	public String createProduct(@RequestBody ProductDto productdto, HttpSession session) throws ErrorHandler {
		return productservice.createProduct(productdto, session);
	}

	@DeleteMapping("/admin/delete/{id}")
	public String deleteProduct(@PathVariable String id, HttpSession session) throws ErrorHandler {
		return productservice.deleteProduct(id, session);
	}

	@GetMapping("/all")
	public ALlProductandCountDTO getAllProduct(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String category, @RequestParam(required = false) Double lt,
			@RequestParam(required = false) Double lte, @RequestParam(required = false) Double gt,
			@RequestParam(required = false) Double gte, @RequestParam(required = false) Integer page

	) throws ErrorHandler {
		System.out.println("keyword " + keyword + " Category " + category);
		
		return productservice.getAllProduct(keyword, category, lt, lte, gt, gte, page);
	}

	@GetMapping("/admin/all")
	public List<Product> getAllProductAdmin(HttpSession session) throws ErrorHandler {
		return productservice.getAllProductsAdmin(session);
	}

	@GetMapping("/{id}")
	public Product getProductdetails(@PathVariable String id) throws ResourceNotFoundException {
		return productservice.getProductById(id);
	}

	@PutMapping("/admin/update/{id}")
	public Product updateProduct(@PathVariable String id, @RequestBody ProductDto product) {
		return productservice.updateProduct(id, product);
	}

	@PutMapping("/review")
	public String createProductReview(@RequestBody ReviewDto reviewDto, HttpSession session)
			throws ProductNotFoundException {
		return productservice.createProductReview(reviewDto, session);
	}

	@GetMapping("/reviews")
	public List<Review> getAllReview(@RequestParam String id) throws ProductNotFoundException {
		return productservice.getProductReviews(id);
	}

	@DeleteMapping("/review")
	public String deleteReview(@RequestParam String productId, @RequestParam String id, HttpSession session)
			throws ProductNotFoundException, ErrorHandler {
		return productservice.deleteReview(productId, id, session);
	}
}
