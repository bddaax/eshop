package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private Payment payment1;
    private Payment payment2;
    private Payment payment3;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        // Create test payment objects
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP12345678ABC");

        Map<String, String> bankData = new HashMap<>();
        bankData.put("bankName", "BCA");
        bankData.put("referenceCode", "REF123");

        payment1 = new Payment("payment-1", "order-1", "VOUCHER_CODE", voucherData, "SUCCESS");
        payment2 = new Payment("payment-2", "order-2", "BANK", bankData, "PENDING");
        payment3 = new Payment("payment-3", "order-1", "BANK", bankData, "REJECTED");
    }

    @Test
    void testSaveNewPayment() {
        Payment savedPayment = paymentRepository.save(payment1);

        assertEquals(payment1.getId(), savedPayment.getId());
        assertNotNull(paymentRepository.findById(payment1.getId()));
    }

    @Test
    void testUpdateExistingPayment() {
        // First save
        paymentRepository.save(payment1);

        // Create updated version with same ID
        Map<String, String> newData = new HashMap<>();
        newData.put("voucherCode", "ESHOP87654321XYZ");
        Payment updatedPayment = new Payment(payment1.getId(), payment1.getOrderId(), payment1.getMethod(), newData, "REJECTED");

        // Update
        Payment result = paymentRepository.save(updatedPayment);

        assertEquals(updatedPayment.getId(), result.getId());
        assertEquals("REJECTED", result.getStatus());
        assertEquals("ESHOP87654321XYZ", result.getPaymentData().get("voucherCode"));

        // Verify from repository
        Payment retrieved = paymentRepository.findById(payment1.getId());
        assertEquals("REJECTED", retrieved.getStatus());
        assertEquals("ESHOP87654321XYZ", retrieved.getPaymentData().get("voucherCode"));
    }

    @Test
    void testFindByIdExisting() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        Payment result = paymentRepository.findById(payment1.getId());

        assertNotNull(result);
        assertEquals(payment1.getId(), result.getId());
    }

    @Test
    void testFindByIdNonExisting() {
        paymentRepository.save(payment1);

        Payment result = paymentRepository.findById("non-existing-id");

        assertNull(result);
    }

    @Test
    void testFindByOrderId() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        paymentRepository.save(payment3);

        List<Payment> result = paymentRepository.findByOrderId("order-1");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getId().equals(payment1.getId())));
        assertTrue(result.stream().anyMatch(p -> p.getId().equals(payment3.getId())));
    }

    @Test
    void testFindByOrderIdNonExisting() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> result = paymentRepository.findByOrderId("non-existing-order");

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> result = paymentRepository.findAll();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getId().equals(payment1.getId())));
        assertTrue(result.stream().anyMatch(p -> p.getId().equals(payment2.getId())));
    }

    @Test
    void testFindAllEmptyRepository() {
        List<Payment> result = paymentRepository.findAll();

        assertTrue(result.isEmpty());
    }
}