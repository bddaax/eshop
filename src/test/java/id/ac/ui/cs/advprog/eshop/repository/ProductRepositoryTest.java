package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testCreateGeneratesNewId() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(1);

        Product created = productRepository.create(product);
        assertNotNull(created.getProductId());
        assertTrue(created.getProductId().length() > 0);
    }

    @Test
    void testEditProductSuccess() {
        Product product = new Product();
        product.setProductName("Sabun Cap Bango");
        product.setProductQuantity(10);
        Product createdProduct = productRepository.create(product);
        String productId = createdProduct.getProductId();

        createdProduct.setProductName("Sabun Cap Bangau");
        createdProduct.setProductQuantity(20);

        Product updatedProduct = productRepository.update(createdProduct);

        assertNotNull(updatedProduct);
        assertEquals("Sabun Cap Bangau", updatedProduct.getProductName());
        assertEquals(20, updatedProduct.getProductQuantity());

        Product retrievedProduct = productRepository.findById(productId);
        assertEquals("Sabun Cap Bangau", retrievedProduct.getProductName());
        assertEquals(20, retrievedProduct.getProductQuantity());
    }

    @Test
    void testEditProductNotFound() {
        Product product = new Product();
        product.setProductId("id-yang-tidak-ada");
        product.setProductName("Produk Tidak Ada");
        product.setProductQuantity(5);

        Product result = productRepository.update(product);

        assertNull(result);
    }

    @Test
    void testUpdateProductWithNullId() {
        // Create a product first
        Product product = new Product();
        product.setProductName("Initial Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        // Try to update with a product that has null ID
        Product productWithNullId = new Product();
        productWithNullId.setProductId(null);
        productWithNullId.setProductName("Updated Product");
        productWithNullId.setProductQuantity(20);

        Product result = productRepository.update(productWithNullId);
        assertNull(result);
    }

    @Test
    void testUpdateProductWithEmptyId() {
        // Create a product first
        Product product = new Product();
        product.setProductName("Initial Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        // Try to update with a product that has empty ID
        Product productWithEmptyId = new Product();
        productWithEmptyId.setProductId("");
        productWithEmptyId.setProductName("Updated Product");
        productWithEmptyId.setProductQuantity(20);

        Product result = productRepository.update(productWithEmptyId);
        assertNull(result);
    }

    @Test
    void testUpdateWithNullProduct() {
        Product result = productRepository.update(null);
        assertNull(result);
    }

    @Test
    void testDeleteProductSuccess() {
        Product product = new Product();
        product.setProductName("Produk Akan Dihapus");
        product.setProductQuantity(100);
        Product createdProduct = productRepository.create(product);
        String productId = createdProduct.getProductId();

        assertNotNull(productRepository.findById(productId));

        productRepository.delete(productId);

        assertNull(productRepository.findById(productId));
    }

    @Test
    void testDeleteProductNotFound() {
        Product product = new Product();
        product.setProductName("Produk Awal");
        product.setProductQuantity(10);
        productRepository.create(product);

        int jumlahAwal = hitungJumlahProduk();

        productRepository.delete("id-tidak-ada");

        int jumlahAkhir = hitungJumlahProduk();
        assertEquals(jumlahAwal, jumlahAkhir);
    }

    @Test
    void testFindProductById() {
        Product product = new Product();
        product.setProductName("Produk Test");
        product.setProductQuantity(15);
        Product createdProduct = productRepository.create(product);

        Product foundProduct = productRepository.findById(createdProduct.getProductId());

        assertNotNull(foundProduct);
        assertEquals(createdProduct.getProductId(), foundProduct.getProductId());
        assertEquals("Produk Test", foundProduct.getProductName());
        assertEquals(15, foundProduct.getProductQuantity());
    }

    @Test
    void testFindProductByIdNotFound() {
        Product foundProduct = productRepository.findById("id-tidak-ada");
        assertNull(foundProduct);
    }

    @Test
    void testDeleteMultipleProducts() {
        // Create multiple products
        Product product1 = new Product();
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        Product created1 = productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductName("Product 2");
        product2.setProductQuantity(20);
        Product created2 = productRepository.create(product2);

        // Delete first product
        productRepository.delete(created1.getProductId());

        // Verify only first product is deleted
        assertNull(productRepository.findById(created1.getProductId()));
        assertNotNull(productRepository.findById(created2.getProductId()));

        // Delete second product
        productRepository.delete(created2.getProductId());

        // Verify both products are deleted
        assertNull(productRepository.findById(created2.getProductId()));
        assertEquals(0, hitungJumlahProduk());
    }

    private int hitungJumlahProduk() {
        Iterator<Product> iterator = productRepository.findAll();
        int jumlah = 0;
        while (iterator.hasNext()) {
            iterator.next();
            jumlah++;
        }
        return jumlah;
    }
}