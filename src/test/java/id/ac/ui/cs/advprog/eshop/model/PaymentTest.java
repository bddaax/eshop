package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        paymentData.put("cardNumber", "4111111111111111");
        paymentData.put("expiryDate", "12/25");
        paymentData.put("cvv", "123");
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Payment payment = new Payment("pay-123", "CREDIT_CARD", paymentData);

        assertEquals("pay-123", payment.getId());
        assertEquals("CREDIT_CARD", payment.getMethod());
        assertEquals("PENDING", payment.getStatus()); // Default status should be PENDING
        assertSame(paymentData, payment.getPaymentData());
        assertEquals("4111111111111111", payment.getPaymentData().get("cardNumber"));
    }

    @Test
    void testCreatePaymentWithRejectedStatus() {
        Payment payment = new Payment("pay-123", "BANK_TRANSFER", paymentData, "REJECTED");

        assertEquals("pay-123", payment.getId());
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWithSuccessStatus() {
        Payment payment = new Payment("pay-123", "PAYPAL", paymentData, "SUCCESS");

        assertEquals("pay-123", payment.getId());
        assertEquals("PAYPAL", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWithPendingStatus() {
        Payment payment = new Payment("pay-123", "CREDIT_CARD", paymentData, "PENDING");

        assertEquals("pay-123", payment.getId());
        assertEquals("CREDIT_CARD", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("pay-123", "CREDIT_CARD", paymentData, "PROCESSING");
        });
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = new Payment("pay-123", "CREDIT_CARD", paymentData, "REJECTED");
        payment.setStatus("SUCCESS");
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("pay-123", "CREDIT_CARD", paymentData);
        payment.setStatus("REJECTED");
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToPending() {
        Payment payment = new Payment("pay-123", "CREDIT_CARD", paymentData, "SUCCESS");
        payment.setStatus("PENDING");
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    }

    @Test
    void testSetInvalidStatus() {
        Payment payment = new Payment("pay-123", "CREDIT_CARD", paymentData);
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("PROCESSING");
        });
    }

    @Test
    void testSetEmptyPaymentData() {
        Payment payment = new Payment("pay-123", "CREDIT_CARD", paymentData);
        Map<String, String> emptyData = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setPaymentData(emptyData);
        });
    }

    @Test
    void testSetValidPaymentData() {
        Payment payment = new Payment("pay-123", "CREDIT_CARD", paymentData);

        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put("accountNumber", "12345678");
        newPaymentData.put("bankName", "Test Bank");

        payment.setPaymentData(newPaymentData);

        assertSame(newPaymentData, payment.getPaymentData());
        assertEquals("12345678", payment.getPaymentData().get("accountNumber"));
        assertEquals("Test Bank", payment.getPaymentData().get("bankName"));
    }

    @Test
    void testDifferentPaymentMethods() {
        Payment creditCardPayment = new Payment("pay-123", "CREDIT_CARD", paymentData);

        Map<String, String> bankData = new HashMap<>();
        bankData.put("accountNumber", "12345678");
        bankData.put("bankName", "Test Bank");

        Payment bankPayment = new Payment("pay-456", "BANK_TRANSFER", bankData);

        assertEquals("CREDIT_CARD", creditCardPayment.getMethod());
        assertEquals("BANK_TRANSFER", bankPayment.getMethod());
        assertEquals("12345678", bankPayment.getPaymentData().get("accountNumber"));
    }

    @Test
    void testPaymentDataAccessibility() {
        Payment payment = new Payment("pay-123", "CREDIT_CARD", paymentData);

        assertEquals("4111111111111111", payment.getPaymentData().get("cardNumber"));
        assertEquals("12/25", payment.getPaymentData().get("expiryDate"));
        assertEquals("123", payment.getPaymentData().get("cvv"));
    }
}