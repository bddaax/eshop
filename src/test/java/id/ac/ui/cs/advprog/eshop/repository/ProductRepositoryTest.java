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

    /* -------------- Test untuk fitur Edit (Update) Product -------------- */

    @Test
    void testEditProductSuccess() {
        // Membuat produk baru untuk diupdate
        Product product = new Product();
        product.setProductName("Sabun Cap Bango");
        product.setProductQuantity(10);
        Product createdProduct = productRepository.create(product);
        String productId = createdProduct.getProductId();

        // Update data produk
        createdProduct.setProductName("Sabun Cap Bangau");
        createdProduct.setProductQuantity(20);

        // Melakukan operasi update
        Product updatedProduct = productRepository.update(createdProduct);

        // Memverifikasi bahwa update berhasil
        assertNotNull(updatedProduct);
        assertEquals("Sabun Cap Bangau", updatedProduct.getProductName());
        assertEquals(20, updatedProduct.getProductQuantity());

        // Memverifikasi bahwa produk benar-benar terupdate di repository
        Product retrievedProduct = productRepository.findById(productId);
        assertEquals("Sabun Cap Bangau", retrievedProduct.getProductName());
        assertEquals(20, retrievedProduct.getProductQuantity());
    }

    @Test
    void testEditProductNotFound() {
        // Membuat produk dengan ID yang tidak ada di repository
        Product product = new Product();
        product.setProductId("id-yang-tidak-ada");
        product.setProductName("Produk Tidak Ada");
        product.setProductQuantity(5);

        // Mencoba update produk yang tidak ada
        Product result = productRepository.update(product);

        // Memverifikasi bahwa update gagal
        assertNull(result);
    }

    /* -------------- Test untuk fitur Delete Product -------------- */

    @Test
    void testDeleteProductSuccess() {
        // Membuat produk untuk dihapus
        Product product = new Product();
        product.setProductName("Produk Akan Dihapus");
        product.setProductQuantity(100);
        Product createdProduct = productRepository.create(product);
        String productId = createdProduct.getProductId();

        // Memverifikasi produk ada sebelum dihapus
        assertNotNull(productRepository.findById(productId));

        // Menghapus produk
        productRepository.delete(productId);

        // Memverifikasi produk sudah tidak ada
        assertNull(productRepository.findById(productId));
    }

    @Test
    void testDeleteProductNotFound() {
        // Menyiapkan kondisi awal dengan sebuah produk
        Product product = new Product();
        product.setProductName("Produk Awal");
        product.setProductQuantity(10);
        productRepository.create(product);

        // Menghitung jumlah produk sebelum delete
        int jumlahAwal = hitungJumlahProduk();

        // Menghapus dengan ID yang tidak ada
        productRepository.delete("id-tidak-ada");

        // Memverifikasi tidak ada produk yang terhapus
        int jumlahAkhir = hitungJumlahProduk();
        assertEquals(jumlahAwal, jumlahAkhir);
    }

    /* -------------- Test tambahan untuk findById -------------- */

    @Test
    void testFindProductById() {
        // Membuat produk baru
        Product product = new Product();
        product.setProductName("Produk Test");
        product.setProductQuantity(15);
        Product createdProduct = productRepository.create(product);

        // Mencari produk berdasarkan ID
        Product foundProduct = productRepository.findById(createdProduct.getProductId());

        // Memverifikasi produk ditemukan
        assertNotNull(foundProduct);
        assertEquals(createdProduct.getProductId(), foundProduct.getProductId());
        assertEquals("Produk Test", foundProduct.getProductName());
        assertEquals(15, foundProduct.getProductQuantity());
    }

    @Test
    void testFindProductByIdNotFound() {
        // Mencari produk dengan ID yang tidak ada
        Product foundProduct = productRepository.findById("id-tidak-ada");

        // Memverifikasi tidak ada produk yang ditemukan
        assertNull(foundProduct);
    }

    // Metode helper untuk menghitung jumlah produk
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