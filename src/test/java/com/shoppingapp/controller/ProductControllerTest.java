package com.shoppingapp.controller;

import com.shoppingapp.exceptions.ProductNotFoundException;
import com.shoppingapp.model.Product;
import com.shoppingapp.service.KafkaProducerService;
import com.shoppingapp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ProductControllerTest {

    @Mock
    private ProductService mockProductService;
    @Mock
    private KafkaProducerService mockKafkaProducerService;

    @InjectMocks
    private ProductController productControllerUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Setup
        // Configure ProductService.getAllProducts(...).
        final Product product = new Product();
        product.setProductId("productId");
        product.setProductName("productName");
        product.setProductDesc("productDesc");
        product.setPrice(0.0f);
        product.setProductStatus("productStatus");
        final List<Product> products = List.of(product);
        when(mockProductService.getAllProducts()).thenReturn(products);

        // Run the test
        final List<Product> result = productControllerUnderTest.getAllProducts();
    }

    @Test
    void testGetAllProducts_ProductServiceReturnsNoItems() {
        // Setup
        when(mockProductService.getAllProducts()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Product> result = productControllerUnderTest.getAllProducts();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testSearchProduct() throws Exception {
        // Setup
        // Configure ProductService.findByProductName(...).
        final Product product = new Product();
        product.setProductId("productId");
        product.setProductName("productName");
        product.setProductDesc("productDesc");
        product.setPrice(0.0f);
        product.setProductStatus("productStatus");
        when(mockProductService.findByProductName("productName")).thenReturn(product);

        // Run the test
        final ResponseEntity<Product> result = productControllerUnderTest.searchProduct("name");
    }

    @Test
    void testSearchProduct_ProductServiceThrowsProductNotFoundException() throws Exception {
        // Setup
        when(mockProductService.findByProductName("productName")).thenThrow(ProductNotFoundException.class);

        // Run the test
        assertThrows(ProductNotFoundException.class, () -> productControllerUnderTest.searchProduct("name"));
    }

    @Test
    void testAddProduct() {
        // Setup
        final Product product = new Product();
        product.setProductId("productId");
        product.setProductName("productName");
        product.setProductDesc("productDesc");
        product.setPrice(0.0f);
        product.setProductStatus("productStatus");

        // Configure ProductService.addProduct(...).
        final Product product1 = new Product();
        product1.setProductId("productId");
        product1.setProductName("productName");
        product1.setProductDesc("productDesc");
        product1.setPrice(0.0f);
        product1.setProductStatus("productStatus");
        when(mockProductService.addProduct(any(Product.class))).thenReturn(product1);

        // Run the test
        final ResponseEntity<?> result = productControllerUnderTest.addProduct(product);

        // Verify the results
        verify(mockProductService).addProduct(any(Product.class));
    }

    @Test
    void testUpdateProduct() {
        // Setup
        // Configure ProductService.updateProductStatus(...).
        final Product product1 = new Product();
        product1.setProductId("productId");
        product1.setProductName("productName");
        product1.setProductDesc("productDesc");
        product1.setPrice(0.0f);
        product1.setProductStatus("productStatus");
        final Optional<Product> product = Optional.of(product1);
        when(mockProductService.updateProductStatus("productName", "productId")).thenReturn(product);

        // Run the test
        final ResponseEntity<?> result = productControllerUnderTest.updateProduct("productName", "productId");

        
    }

    @Test
    void testUpdateProduct_ProductServiceReturnsAbsent() {
        // Setup
        when(mockProductService.updateProductStatus("productName", "productId")).thenReturn(Optional.empty());

        // Run the test
        final ResponseEntity<?> result = productControllerUnderTest.updateProduct("productName", "productId");
    }

    @Test
    void testDeleteProductByIdAndProductName() {
        // Setup
        // Run the test
        final ResponseEntity<?> result = productControllerUnderTest.deleteProductByIdAndProductName("productName", "productId");

        // Verify the results
        verify(mockProductService).deleteProductByIdAndProductName("productName", "productId");
    }
}
