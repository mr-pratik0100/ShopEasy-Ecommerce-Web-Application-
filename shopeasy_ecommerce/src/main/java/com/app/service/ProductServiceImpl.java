package com.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.collections.Product;
import com.app.collections.Review;
import com.app.collections.User;
import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.ProductNotFoundException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ALlProductandCountDTO;
import com.app.dto.ProductDto;
import com.app.dto.ReviewDto;
import com.app.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public String createProduct(ProductDto productdto, HttpSession session) throws ErrorHandler {

//		// Authorize user("Admin")
		User storedUser = (User) session.getAttribute("user");
		String userRole = storedUser.getRole();
		if (userRole.equals("Admin")) {

		} else {
			throw new ErrorHandler(storedUser.getName() + " is not allowed to access this resource");
		}

		Product prod = mapper.map(productdto, Product.class);
		prod.setUser(storedUser.getId());
		productRepo.save(prod);
		return "product added successfully ";
	}

	@Override
	public String deleteProduct(String id, HttpSession session) throws ErrorHandler {
		String mesg = "Please enter a valid id";

		// Authorize user("Admin")
		User storedUser = (User) session.getAttribute("user");
		String userRole = storedUser.getRole();
		if (userRole.equals("Admin")) {

		} else {
			throw new ErrorHandler(storedUser.getName() + " is not allowed to access this resource");
		}

		if (productRepo.existsById(id)) {
			productRepo.deleteById(id);
			mesg = "Product deleted successfully";
		}
		return mesg;
	}

	@Override
	public ALlProductandCountDTO getAllProduct(String keyword, String category, Double lt, Double lte, Double gt,
			Double gte, Integer page) throws ErrorHandler {
		List<Product> product = productRepo.findAll();
		int count = (int) productRepo.count();

		Map<String, Product> m = new HashMap<>();

		if (keyword != null && category != null) {

			productRepo.findByRegexNameAndCat(keyword, category).forEach(pm -> m.put(pm.getId(), pm));
		} else if (keyword != null) {

			productRepo.findByRegexName(keyword).forEach(pm -> m.put(pm.getId(), pm));
		} else if (category != null) {

			productRepo.findByRegexCat(category).forEach(pm -> m.put(pm.getId(), pm));
		}

		if (gt != null && lt != null) {

			productRepo.findByPriceGreaterThanAndLessThan(gt, lt).forEach(pm -> m.put(pm.getId(), pm));
		} else if (gte != null && lte != null) {

			productRepo.findByPriceGreaterThanEqualtoAndLessThanEqualto(gte, lte).forEach(pm -> m.put(pm.getId(), pm));
		} else if (gte != null && lt != null) {

			productRepo.findByPriceGreaterThanEqualtoAndLessThan(gte, lt).forEach(pm -> m.put(pm.getId(), pm));
		} else if (gt != null && lte != null) {

			productRepo.findByPriceGreaterThanAndLessThanEqualto(gt, lte).forEach(pm -> m.put(pm.getId(), pm));
		} else if (lt != null) {

			productRepo.findByPriceLessThan(lt).forEach(pm -> m.put(pm.getId(), pm));
		} else if (lte != null) {

			productRepo.findByPriceLessThanEqual(lte).forEach(pm -> m.put(pm.getId(), pm));
		} else if (gt != null) {

			productRepo.findByPriceGreaterThan(gt).forEach(pm -> m.put(pm.getId(), pm));

			productRepo.findByPriceGreaterThanEqual(gte).forEach(pm -> m.put(pm.getId(), pm));
		}

		if (m.isEmpty()) {
			// PAGINATION
			int resultPerPage = 8;
			int currentPage;
			if (page == null) {
				currentPage = 0;
			} else {
				currentPage = page;
			}
			Pageable pageable = PageRequest.of(0, resultPerPage);
			Page<Product> pg = productRepo.findAll(pageable);
			return new ALlProductandCountDTO(pg.getContent(), count);
		}

//		return new ArrayList<>(p);
		return new ALlProductandCountDTO(new ArrayList<>(m.values()), count);

	}

	@Override
	public List<Product> getAllProductsAdmin(HttpSession session) throws ErrorHandler {
		// Authorize user("Admin")
		User storedUser = (User) session.getAttribute("user");
		String userRole = storedUser.getRole();
		if (userRole.equals("Admin")) {

		} else {
			throw new ErrorHandler(storedUser.getName() + " is not allowed to access this resource");
		}
		return productRepo.findAll();
	}

	@Override
	public Product getProductById(String id) throws ResourceNotFoundException {
		return productRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Emp ID , Can't get emp details!!!!"));
	}

	@Override
	public Product updateProduct(String id, ProductDto productDto) {

		Optional<Product> opProduct = productRepo.findById(id);

		if (opProduct.isPresent()) {
			Product product = opProduct.get();
			product.setProdName(productDto.getProdName());
			product.setCategory(productDto.getCategory());
			product.setDescription(productDto.getDescription());
			product.setCreatedAt(productDto.getCreatedAt());
			product.setImage(productDto.getImage());
			product.setPrice(productDto.getPrice());
			product.setStock(productDto.getStock());

			return productRepo.save(product);
		}
		return null;
	}

	@Override
	public String createProductReview(ReviewDto reviewDto, HttpSession session) throws ProductNotFoundException {
		User storedUser = (User) session.getAttribute("user");

		Review review = new Review();
		review.setUser(storedUser.getId());
		review.setName(storedUser.getName());
		review.setRating(reviewDto.getRating());
		review.setComment(reviewDto.getComment());

		Optional<Product> opProduct = productRepo.findById(reviewDto.getProductId());
		if (opProduct == null) {
			throw new ProductNotFoundException("Product Not found");
		}
		Product product = opProduct.get();
		System.out.println("Product Name=== " + product.getProdName());
		System.out.println("Review List" + product.getReview());
		boolean isReviewed = product.getReview().stream().anyMatch(p -> p.getUser().equals(storedUser.getId()));

		if (isReviewed) {
			for (Review r : product.getReview()) {
				if (storedUser.getId().equals(r.getUser())) {
					r.setComment(reviewDto.getComment());
					r.setRating(reviewDto.getRating());
				}
			}
		} else {
			List<Review> existingReviews = product.getReview();
			existingReviews.add(review);
			product.setReview(existingReviews);
			product.setNoOfReviews(existingReviews.size());
		}
		int avg = 0;
		for (Review r : product.getReview()) {
			avg += r.getRating();
		}
		product.setRating(avg / product.getReview().size());

		productRepo.save(product);

		return "Review added";
	}

	@Override
	public List<Review> getProductReviews(String id) throws ProductNotFoundException {
		Optional<Product> opProduct = productRepo.findById(id);
		if (opProduct == null) {
			throw new ProductNotFoundException("Product Not found");
		}
		Product product = opProduct.get();
		return product.getReview();

	}

	@Override
	public String deleteReview(String productId, String id, HttpSession session)
			throws ProductNotFoundException, ErrorHandler {

		User storedUser = (User) session.getAttribute("user");

		if (storedUser == null) {
			throw new ErrorHandler("To Order this Product Login first");
		}

		Optional<Product> opProduct = productRepo.findById(productId);
		if (opProduct == null) {
			throw new ProductNotFoundException("Product Not found");
		}
		Product product = opProduct.get();
		List<Review> updatedReview = product.getReview().stream().filter(r -> !r.getUser().equals(id))
				.collect(Collectors.toList());

		product.setReview(updatedReview);

		int avg = 0;
		for (Review r : product.getReview()) {
			avg += r.getRating();
		}
		if (updatedReview.size() == 0) {
			product.setRating(0);
		} else {
			product.setRating(avg / product.getReview().size());
		}
		product.setNoOfReviews(updatedReview.size());
		productRepo.save(product);

		return "Review Deleted successfully";
	}

}
