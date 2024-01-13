package ru.rail.gmarketonline.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;
import org.springframework.transaction.annotation.Transactional;
import ru.rail.gmarketonline.dto.ProductDto;
import ru.rail.gmarketonline.service.ProductService;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
public class ProductServiceIT {
    @Autowired
    private ProductService productService;

    @Test
    public void testGetAllProducts() throws Exception {
        List<ProductDto> products = productService.getAllProducts();
        assertFalse(products.isEmpty(), "Products list should not be empty");
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        ProductDto product = productService.getProductById(productId).orElse(null);
        assertNotNull(product, "Product should not be null");
        assertEquals(productId, product.getId(), "Product ID should match");

    }

    @Test
    public void testAddProduct() {
        // Set
        String productName = "testProductName";
        String description = "descriptionTest";
        Double price = 19.0;
        Integer quantity = 100;
        //Set
        ProductDto productDto = ProductDto.builder()
                .productName(productName)
                .description(description)
                .price(price)
                .quantity(quantity)
                .build();

        // Act
        productService.addProduct(productDto);

        // Assert
        AssertionErrors.assertEquals("Product name should match", productDto.getProductName(), "testProductName");
        AssertionErrors.assertEquals("Product description should match", productDto.getDescription(), "descriptionTest");
        AssertionErrors.assertEquals("Price should match", productDto.getPrice(), 19.0);
        AssertionErrors.assertEquals("Quantity should match", productDto.getQuantity(), 100);

    }

}

