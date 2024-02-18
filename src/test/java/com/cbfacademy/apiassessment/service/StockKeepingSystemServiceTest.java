package com.cbfacademy.apiassessment.service;

import com.cbfacademy.apiassessment.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StockKeepingSystemServiceTest {

    private StockKeepingSystemService service;

    @BeforeEach
    void setUp() throws IOException {
        // Use a temporary file that gets deleted after tests
        String tempFileName = Files.createTempFile("test_products", ".json").toString();
        service = new StockKeepingSystemService(tempFileName);
        // Ensure the file is deleted after tests to avoid clutter
        new File(tempFileName).deleteOnExit();
    }

    @Test
    void testAddAndGetProduct() {
        Product product = new Product(null, "Test Product", "Description", BigDecimal.valueOf(100), 10, 20, "Category", "Supplier", null);
        Product addedProduct = service.addProduct(product);
        UUID productId = addedProduct.getId();

        Optional<Product> retrievedProduct = service.getProductById(productId);

        assertTrue(retrievedProduct.isPresent());
        assertEquals(product.getName(), retrievedProduct.get().getName());
    }

    @Test
    void testUpdateProduct() {
        Product originalProduct = new Product(null, "Original", "Original Description", BigDecimal.valueOf(100), 10, 20, "Category", "Supplier", null);
        Product addedOriginalProduct = service.addProduct(originalProduct);
        UUID productId = addedOriginalProduct.getId();

        Product updatedProduct = new Product(productId, "Updated", "Updated Description", BigDecimal.valueOf(200), 5, 15, "Updated Category", "Updated Supplier", null);
        service.updateProduct(productId, updatedProduct);

        Optional<Product> result = service.getProductById(productId);
        assertTrue(result.isPresent());
        assertEquals("Updated", result.get().getName());
        assertEquals(updatedProduct.getDescription(), result.get().getDescription());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product(null, "To Delete", "To be deleted", BigDecimal.valueOf(50), 5, 10, "Category", "Supplier", null);
        Product addedProduct = service.addProduct(product);
        UUID productId = addedProduct.getId();

        boolean isDeleted = service.deleteProduct(productId);
        assertTrue(isDeleted);
        assertFalse(service.getProductById(productId).isPresent());
    }

    @Test
    void testGetAllProducts() {
        service.addProduct(new Product(null, "Product 1", "Description 1", BigDecimal.valueOf(100), 10, 20, "Category", "Supplier", null));
        service.addProduct(new Product(null, "Product 2", "Description 2", BigDecimal.valueOf(200), 20, 40, "Category", "Supplier", null));

        List<Product> allProducts = service.getAllProducts();
        assertEquals(2, allProducts.size());
    }
}
