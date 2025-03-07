package id.ac.ui.cs.advprog.eshop.model;

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
        paymentData.put("cardHolderName", "John Doe");
    }

    @Test
    void testCreatePaymentDefault() {
        Payment payment = new Payment("pay-123", "CREDIT_CARD", paymentData);

        assertNotNull(payment);
        assertEquals("pay-123", payment.getId());
        assertEquals("CREDIT_CARD", payment.getMethod());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWithStatus() {
        Payment payment = new Payment("pay-123", "BANK_TRANSFER", paymentData, "PENDING");

        assertNotNull(payment);
        assertEquals("pay-123", payment.getId());
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
    }

    @Test
    void testBuilderPattern() {
        Payment payment = Payment.builder()
                .id("pay-456")
                .method("PAYPAL")
                .status("COMPLETED")
                .paymentData(paymentData)
                .build();

        assertEquals("pay-456", payment.getId());
        assertEquals("PAYPAL", payment.getMethod());
        assertEquals("COMPLETED", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testSetStatus() {
        Payment payment = Payment.builder()
                .id("pay-789")
                .method("CREDIT_CARD")
                .status("PENDING")
                .paymentData(paymentData)
                .build();

        payment.setStatus("COMPLETED");
        assertEquals("COMPLETED", payment.getStatus());
    }

    @Test
    void testPaymentDataContent() {
        Payment payment = Payment.builder()
                .id("pay-123")
                .method("CREDIT_CARD")
                .status("PENDING")
                .paymentData(paymentData)
                .build();

        Map<String, String> data = payment.getPaymentData();
        assertEquals("4111111111111111", data.get("cardNumber"));
        assertEquals("12/25", data.get("expiryDate"));
        assertEquals("123", data.get("cvv"));
        assertEquals("John Doe", data.get("cardHolderName"));
    }

    @Test
    void testDifferentPaymentMethods() {
        Payment creditCardPayment = Payment.builder()
                .id("pay-123")
                .method("CREDIT_CARD")
                .paymentData(paymentData)
                .build();

        Map<String, String> bankData = new HashMap<>();
        bankData.put("accountNumber", "12345678");
        bankData.put("bankName", "Test Bank");

        Payment bankPayment = Payment.builder()
                .id("pay-456")
                .method("BANK_TRANSFER")
                .paymentData(bankData)
                .build();

        assertEquals("CREDIT_CARD", creditCardPayment.getMethod());
        assertEquals("BANK_TRANSFER", bankPayment.getMethod());
        assertEquals("12345678", bankPayment.getPaymentData().get("accountNumber"));
    }
}