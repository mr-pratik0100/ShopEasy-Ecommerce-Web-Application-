package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.app.collections.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
	@Query("{'prodName' : ?0}")
	public Optional<Product> findByProdName(String productName);

	@Query("{'prodName':{$regex:?0, $options:'i'}}")
	List<Product> findByRegexName(String keyword);

	@Query("{'category':{$regex:?0, $options:'i'}}")
	List<Product> findByRegexCat(String keyword);

	@Query("{'prodName':{$regex:?0, $options:'i'},'category':{$regex:?1, $options:'i'}}")
	List<Product> findByRegexNameAndCat(String keyword, String category);

	@Query("{'price':{$lt:?0}}")
	List<Product> findByPriceLessThan(Double price);

	@Query("{'price':{$lte:?0}}")
	List<Product> findByPriceLessThanEqual(Double price);

	@Query("{'price':{$gt:?0}}")
	List<Product> findByPriceGreaterThan(Double price);

	@Query("{'price':{$gte:?0}}")
	List<Product> findByPriceGreaterThanEqual(Double price);

	@Query("{'price': {$gt: ?0, $lt: ?1}}")
	List<Product> findByPriceGreaterThanAndLessThan(Double gt, Double lt);

	@Query("{'price': {$gte: ?0, $lte: ?1}}")
	List<Product> findByPriceGreaterThanEqualtoAndLessThanEqualto(Double gte, Double lte);

	@Query("{'price': {$gt: ?0, $lte: ?1}}")
	List<Product> findByPriceGreaterThanAndLessThanEqualto(Double gt, Double lte);

	@Query("{'price': {$gte: ?0, $lt: ?1}}")
	List<Product> findByPriceGreaterThanEqualtoAndLessThan(Double gte, Double lt);


	

}
