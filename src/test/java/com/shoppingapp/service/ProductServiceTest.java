package com.shoppingapp.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.shoppingapp.exceptions.ProductNotFoundException;
import com.shoppingapp.model.Product;
import com.shoppingapp.repository.ProductRepository;

class ProductServiceTest {

    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private MongoTemplate mockMongoTemplate;

    @InjectMocks
    private ProductService productServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Setup
        // Run the test
        final List<Product> result = productServiceUnderTest.getAllProducts();

        // Verify the results
    }

    @Test
    void testFindByProductName() throws Exception {
        // Setup
        // Run the test
        final Product result = productServiceUnderTest.findByProductName("productName");

        // Verify the results
    }

    @Test
    void testFindByProductName_ThrowsProductNotFoundException() {
        // Setup
        // Run the test
        assertThrows(ProductNotFoundException.class, () -> productServiceUnderTest.findByProductName("productName"));
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

        // Run the test
        final Product result = productServiceUnderTest.addProduct(product);

        // Verify the results
    }

    @Test
    void testUpdateProductStatus() {
        // Setup
        // Run the test
        final Optional<Product> result = productServiceUnderTest.updateProductStatus("productName", "productId");

        // Verify the results
    }

    @Test
    void testDeleteProductByIdAndProductName() {
        // Setup
        // Run the test
        productServiceUnderTest.deleteProductByIdAndProductName("productName", "productId");

        // Verify the results
    }
}
