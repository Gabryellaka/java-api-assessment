package com.cbfacademy.apiassessment.service;

import com.cbfacademy.apiassessment.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Service
public class StockKeepingSystemService {
    private String fileName;
    private Map<UUID, Product> productMap = new HashMap<>();

    public StockKeepingSystemService(@Value("${product.data.file:products.json}") String fileName) {
        this.fileName = fileName;
    }


    // Method to retrieve all products
    public List<Product> getAllProducts() {
        return new ArrayList<>(productMap.values());
    }

    // Method to retrieve a product by ID
    public Optional<Product> getProductById(UUID productId) {
        return Optional.ofNullable(productMap.get(productId));
    }

    // Method to add a new product
    public Product addProduct(Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID()); // Atribuir novo UUID se não tiver ID.
        }
        if (productMap.containsKey(product.getId())) {
            throw new IllegalArgumentException("Product with ID " + product.getId() + " already exists.");
        }
        productMap.put(product.getId(), product);
        saveProductsToFile();
        return product;
    }

    // Method to update an existing product
    public Optional<Product> updateProduct(UUID productId, Product updatedProduct) {
        if (!productId.equals(updatedProduct.getId())) {
            throw new IllegalArgumentException("Mismatch between path variable ID and product's ID.");
        }
        if (productMap.containsKey(productId)) {
            productMap.put(productId, updatedProduct); // Assume que updatedProduct tem o mesmo ID
            saveProductsToFile();
            return Optional.of(updatedProduct);
        }
        return Optional.empty();
    }

    // Method to delete a product
    public boolean deleteProduct(UUID productId) {
        if (productMap.containsKey(productId)) {
            productMap.remove(productId);
            saveProductsToFile();
            return true;
        }
        return false;
    }

    // Save the productMap to a JSON file
    @PreDestroy
    public void saveProductsToFile() {
        System.out.println("Saving products to file: " + new File(fileName).getAbsolutePath());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            mapper.writeValue(new File(fileName), productMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the productMap from a JSON file
    @PostConstruct
    public void loadProductsFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        System.out.println("Loading products from file: " + new File(fileName).getAbsolutePath());
        try {
            File file = new File(fileName);
            if (file.exists()) {
                productMap = mapper.readValue(file, new com.fasterxml.jackson.core.type.TypeReference<Map<UUID, Product>>(){});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

