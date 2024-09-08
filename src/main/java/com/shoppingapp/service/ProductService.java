package com.shoppingapp.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.shoppingapp.exceptions.ProductNotFoundException;
import com.shoppingapp.model.Product;
import com.shoppingapp.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private static final Logger log = LogManager.getLogger(ProductService.class);
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Product findByProductName(String productName) throws ProductNotFoundException{
		Query query = new Query(Criteria.where("productName").is(productName));
		return mongoTemplate.findOne(query, Product.class);
	}
	
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

	public Optional<Product> updateProductStatus(String productName, String productId) {
		Product product = null;
		try {
			Query query = new Query(Criteria.where("productName").is(productName).and("_id").is(productId));
			product = mongoTemplate.findOne(query, Product.class);
			if(null != product && !product.getProductName().isEmpty()) {
				product.setProductStatus("OUT OF STOCK");
				mongoTemplate.save(product);
			}
		}catch(Exception e) {
			log.error("Exception in updateProductStatus() in ProductService");
			throw e;
		}
		return Optional.of(product);
	}
	
	public void deleteProductByIdAndProductName(String productName, String productId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("productName").is(productName).and("_id").is(productId));
			
			mongoTemplate.findAndRemove(query, Product.class);
		}catch(Exception e) {
			log.error("Exception in deleteProductByIdAndProductName() in ProductService");
			throw e;
		}
	}
}
