package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    Product product;

    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }

    @Test
    void testSetProductId() {
        String newId = "new-id-123";
        this.product.setProductId(newId);
        assertEquals(newId, this.product.getProductId());
    }

    @Test
    void testSetProductName() {
        String newName = "Sampo Cap Usep";
        this.product.setProductName(newName);
        assertEquals(newName, this.product.getProductName());
    }

    @Test
    void testSetProductQuantity() {
        int newQuantity = 50;
        this.product.setProductQuantity(newQuantity);
        assertEquals(newQuantity, this.product.getProductQuantity());
    }

    @Test
    void testSetNullProductId() {
        this.product.setProductId(null);
        assertNull(this.product.getProductId());
    }

    @Test
    void testSetNullProductName() {
        this.product.setProductName(null);
        assertNull(this.product.getProductName());
    }

    @Test
    void testSetZeroQuantity() {
        this.product.setProductQuantity(0);
        assertEquals(0, this.product.getProductQuantity());
    }

    @Test
    void testSetNegativeQuantity() {
        this.product.setProductQuantity(-1);
        assertEquals(-1, this.product.getProductQuantity());
    }

    @Test
    void testNewProductDefaultValues() {
        Product newProduct = new Product();
        assertNull(newProduct.getProductId());
        assertNull(newProduct.getProductName());
        assertEquals(0, newProduct.getProductQuantity());
    }
}