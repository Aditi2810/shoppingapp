package com.shoppingapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingapp.model.Product;
import com.shoppingapp.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/api/v1.0/shoppingapp")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	private static final Logger log = LogManager.getLogger(ProductController.class);
	
	@GetMapping(value="/all")
	public List<Product> getAllProducts(){
		List<Product> products = new ArrayList<>();
		log.info("Inside getAllProducts() in ProductController.");
		try {
			products =  productService.getAllProducts();
			
		}catch(Exception e) {
			log.error("Exception in getAllProducts() in ProductController.", e);
		}
		return products;
	}
	
	@GetMapping(value="/products/search/{productName}")
	public ResponseEntity<Product> searchProduct(@PathVariable("productName") String name) {
		log.info("Inside searchProduct() in ProductController.");
		Product product = null;
		try {
			if(null != name && !name.isEmpty()) {
				product = productService.findByProductName(name);
			} 
		}catch(Exception e) {
			log.error("Exception in searchProduct() in ProductController.", e);
		}
		return new ResponseEntity<Product>(product,HttpStatus.OK);
	}
	
	@PostMapping(value = "/product/add")
	public ResponseEntity<?> addProduct(@Valid @RequestBody Product product){
		try {
			if(null != product && !product.getProductName().isEmpty()) {
				productService.addProduct(product);
			}
			
		}catch(Exception e) {
			log.error("Exception in addProduct() in ProductController.", e);
		}
		return ResponseEntity.ok("Product added Successfully");
		
	}
	
	@PutMapping(value="/{productName}/update/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable("productName") String productName, @PathVariable("productId") String productId ){
		try {
			if(!productName.isEmpty() && !productId.isEmpty()) {
				Optional<Product> p = productService.updateProductStatus(productName,productId);
				if(!p.isPresent()) {
					return ResponseEntity.ok("Product not found");
				}
			}
		}catch(Exception e) {
			log.error("Exception in updateProduct() in ProductController.", e);
		}
		return ResponseEntity.ok("Product updated Successfully");
	}
	
	@DeleteMapping(value="{productName}/delete/{productId}")
	public ResponseEntity<?> deleteProductByIdAndProductName(@PathVariable("productName") String productName, @PathVariable("productId")  String productId ){
		try {
			if(!productName.isEmpty() && !productId.isEmpty()) {
				productService.deleteProductByIdAndProductName(productName,productId);
			}
		}catch(Exception e) {
			log.error("Exception in deleteProductByIdAndProductName() in ProductController.", e);
		}
		return ResponseEntity.ok("Product deleted Successfully");
	}
}
