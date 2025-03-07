package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentMethod;
import enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private String validId;
    private String validOrderId;
    private String validMethod;
    private Map<String, String> validPaymentData;

    @BeforeEach
    void setUp() {
        validId = "payment-123";
        validOrderId = "order-123";
        validMethod = "VOUCHER_CODE";
        validPaymentData = new HashMap<>();
        validPaymentData.put("voucherCode", "ESHOP12345678ABC");
    }

    @Test
    void testConstructorWithStatus() {
        String status = PaymentStatus.SUCCESS.getValue();
        Payment payment = new Payment(validId, validOrderId, validMethod, validPaymentData, status);

        assertEquals(validId, payment.getId());
        assertEquals(validOrderId, payment.getOrderId());
        assertEquals(validMethod, payment.getMethod());
        assertEquals(validPaymentData, payment.getPaymentData());
        assertEquals(status, payment.getStatus());
    }

    @Test
    void testConstructorWithoutStatus() {
        Payment payment = new Payment(validId, validOrderId, validMethod, validPaymentData);

        assertEquals(validId, payment.getId());
        assertEquals(validOrderId, payment.getOrderId());
        assertEquals(validMethod, payment.getMethod());
        assertEquals(validPaymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    }

    @Test
    void testSetValidStatus() {
        Payment payment = new Payment(validId, validOrderId, validMethod, validPaymentData);
        String newStatus = PaymentStatus.SUCCESS.getValue();

        payment.setStatus(newStatus);

        assertEquals(newStatus, payment.getStatus());
    }

    @Test
    void testSetInvalidStatus() {
        Payment payment = new Payment(validId, validOrderId, validMethod, validPaymentData);
        String invalidStatus = "INVALID_STATUS";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus(invalidStatus);
        });

        assertTrue(exception.getMessage().contains("Invalid payment status"));
    }

    @Test
    void testSetValidMethod() {
        Payment payment = new Payment(validId, validOrderId, validMethod, validPaymentData);
        String newMethod = PaymentMethod.BANK.name();

        payment.setMethod(newMethod);

        assertEquals(newMethod, payment.getMethod());
    }

    @Test
    void testSetInvalidMethod() {
        Payment payment = new Payment(validId, validOrderId, validMethod, validPaymentData);
        String invalidMethod = "INVALID_METHOD";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            payment.setMethod(invalidMethod);
        });

        assertTrue(exception.getMessage().contains("Invalid payment method"));
    }

    @Test
    void testSetMethodCaseInsensitive() {
        Payment payment = new Payment(validId, validOrderId, validMethod, validPaymentData);
        String lowerCaseMethod = "bank";

        payment.setMethod(lowerCaseMethod);

        assertEquals(PaymentMethod.BANK.name(), payment.getMethod());
    }

    @Test
    void testSetValidPaymentData() {
        Payment payment = new Payment(validId, validOrderId, validMethod, validPaymentData);
        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put("bankName", "BCA");
        newPaymentData.put("referenceCode", "REF123");

        payment.setPaymentData(newPaymentData);

        assertEquals(newPaymentData, payment.getPaymentData());
    }

    @Test
    void testSetEmptyPaymentData() {
        Payment payment = new Payment(validId, validOrderId, validMethod, validPaymentData);
        Map<String, String> emptyPaymentData = new HashMap<>();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            payment.setPaymentData(emptyPaymentData);
        });

        assertTrue(exception.getMessage().contains("Payment data cannot be empty"));
    }

    @Test
    void testSetNullPaymentData() {
        Payment payment = new Payment(validId, validOrderId, validMethod, validPaymentData);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            payment.setPaymentData(null);
        });

        assertTrue(exception.getMessage().contains("Payment data cannot be empty"));
    }
}