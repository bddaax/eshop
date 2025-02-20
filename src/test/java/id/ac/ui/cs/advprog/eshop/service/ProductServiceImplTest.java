package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.create(product)).thenReturn(product);

        Product created = productService.create(product);

        assertNotNull(created);
        assertEquals(product.getProductId(), created.getProductId());
        assertEquals(product.getProductName(), created.getProductName());
        assertEquals(product.getProductQuantity(), created.getProductQuantity());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAllProducts() {
        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);

        List<Product> productList = Arrays.asList(product, product2);
        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> found = productService.findAll();

        assertNotNull(found);
        assertEquals(2, found.size());
        assertEquals(product.getProductId(), found.get(0).getProductId());
        assertEquals(product2.getProductId(), found.get(1).getProductId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindAllWhenEmpty() {
        when(productRepository.findAll()).thenReturn(Arrays.<Product>asList().iterator());

        List<Product> found = productService.findAll();

        assertNotNull(found);
        assertTrue(found.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        Product found = productService.findById(product.getProductId());

        assertNotNull(found);
        assertEquals(product.getProductId(), found.getProductId());
        assertEquals(product.getProductName(), found.getProductName());
        assertEquals(product.getProductQuantity(), found.getProductQuantity());
        verify(productRepository, times(1)).findById(product.getProductId());
    }

    @Test
    void testFindByIdNotFound() {
        String nonExistentId = "non-existent-id";
        when(productRepository.findById(nonExistentId)).thenReturn(null);

        Product found = productService.findById(nonExistentId);

        assertNull(found);
        verify(productRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void testUpdateProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(200);

        when(productRepository.update(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.update(updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdateProductNotFound() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");

        when(productRepository.update(nonExistentProduct)).thenReturn(null);

        Product result = productService.update(nonExistentProduct);

        assertNull(result);
        verify(productRepository, times(1)).update(nonExistentProduct);
    }

    @Test
    void testDeleteProduct() {
        String productId = product.getProductId();
        doNothing().when(productRepository).delete(productId);

        productService.delete(productId);

        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    void testDeleteNonExistentProduct() {
        String nonExistentId = "non-existent-id";
        doNothing().when(productRepository).delete(nonExistentId);

        productService.delete(nonExistentId);

        verify(productRepository, times(1)).delete(nonExistentId);
    }
}