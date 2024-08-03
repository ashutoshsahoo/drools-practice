package com.ashu.practice;

import com.ashu.practice.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProductRuleTest {

    private ProductService productService;

    @BeforeEach
    public void setup() {
        productService = new ProductService();
    }

    @Test
    void whenProductTypeElectronic_ThenLabelBarcode() {
        Product product = new Product("Microwave", "Electronic");
        product = productService.applyLabelToProduct(product);
        assertEquals("BarCode", product.getLabel());
    }

    @Test
    void whenProductTypeBook_ThenLabelIsbn() {
        Product product = new Product("AutoBiography", "Book");
        product = productService.applyLabelToProduct(product);
        assertEquals("ISBN", product.getLabel());
    }
}
