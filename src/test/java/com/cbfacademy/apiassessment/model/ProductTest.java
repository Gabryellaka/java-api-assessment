package com.cbfacademy.apiassessment.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void productConstructorAndGetterTest() {
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(null, "Test Product", "A test product", BigDecimal.valueOf(99.99), 10, 20, "Electronics", "Test Supplier", now);

        assertNotNull(product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("A test product", product.getDescription());
        assertEquals(BigDecimal.valueOf(99.99), product.getPrice());
        assertEquals(10, product.getMinimumQuantity());
        assertEquals(20, product.getMaximumQuantity());
        assertTrue(product.getCreatedOn().isEqual(now) || product.getCreatedOn().isAfter(now));
        assertEquals("Electronics", product.getCategory());
        assertEquals("Test Supplier", product.getSupplier());
    }

    @Test
    void productEquals() {
        Product product1 = new Product(null, "Product", "Description", BigDecimal.ONE, 1, 100, "Category", "Supplier", null);
        Product product2 = product1;

        assertEquals(product1, product2);
    }

    @Test
    void productHashCode() {
        Product product1 = new Product(null, "Product", "Description", BigDecimal.ONE, 1, 100, "Category", "Supplier", null);
        Product product2 = new Product(null, "Product", "Description", BigDecimal.ONE, 1, 100, "Category", "Supplier", null);

        
        assertNotEquals(product1.hashCode(), product2.hashCode(), "Expected different hashCodes for different product instances.");
    }

    @Test
    void productToString() {
        Product product = new Product(null, "Product", "Description", BigDecimal.ONE, 1, 100, "Category", "Supplier", null);
        assertTrue(product.toString().startsWith("Product{id="));
    }
}
