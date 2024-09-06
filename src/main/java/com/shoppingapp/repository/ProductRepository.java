package com.shoppingapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoppingapp.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

	@Query("Select p from Products p where p.productName LIKE %:productName%")
	public Product findByProductName(@Param("productName") String productName);
	
	@Query("Select p from Products p where p.productName = :productName AND p._id = :productId")
	public Product findByProductNameAndId(@Param("productName") String productName, @Param("_id") String productId);
}