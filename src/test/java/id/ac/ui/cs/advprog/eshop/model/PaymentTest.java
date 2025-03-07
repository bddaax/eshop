package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;
    private Map<String, String> bankData;
    private Map<String, String> validVoucherData;
    private Map<String, String> invalidVoucherData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        paymentData.put("cardNumber", "4111111111111111");
        paymentData.put("expiryDate", "12/25");
        paymentData.put("cvv", "123");

        this.bankData = new HashMap<>();
        bankData.put("bankName", "Test Bank");
        bankData.put("referenceCode", "REF123456");

        this.validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP1234ABCD5678");

        this.invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "INVALID1234");
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Payment payment = new Payment("pay-123", "BANK", bankData);

        assertEquals("pay-123", payment.getId());
        assertEquals("BANK", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertSame(bankData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWithRejectedStatus() {
        Payment payment = new Payment("pay-123", "BANK", bankData, "REJECTED");

        assertEquals("pay-123", payment.getId());
        assertEquals("BANK", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(bankData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWithSuccessStatus() {
        Payment payment = new Payment("pay-123", "VOUCHER_CODE", validVoucherData, "SUCCESS");

        assertEquals("pay-123", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertSame(validVoucherData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWithPendingStatus() {
        Payment payment = new Payment("pay-123", "BANK", bankData, "PENDING");

        assertEquals("pay-123", payment.getId());
        assertEquals("BANK", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertSame(bankData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("pay-123", "BANK", bankData, "PROCESSING");
        });
    }

    @Test
    void testCreatePaymentWithInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("pay-123", "CREDIT_CARD", paymentData);
        });
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = new Payment("pay-123", "BANK", bankData, "REJECTED");
        payment.setStatus("SUCCESS");
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("pay-123", "BANK", bankData);
        payment.setStatus("REJECTED");
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToPending() {
        Payment payment = new Payment("pay-123", "BANK", bankData, "SUCCESS");
        payment.setStatus("PENDING");
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    }

    @Test
    void testSetInvalidStatus() {
        Payment payment = new Payment("pay-123", "BANK", bankData);
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("PROCESSING");
        });
    }

    @Test
    void testSetValidMethod() {
        Payment payment = new Payment("pay-123", "BANK", bankData);
        payment.setMethod("VOUCHER_CODE");
        assertEquals("VOUCHER_CODE", payment.getMethod());
    }

    @Test
    void testSetInvalidMethod() {
        Payment payment = new Payment("pay-123", "BANK", bankData);
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setMethod("CREDIT_CARD");
        });
    }

    @Test
    void testSetEmptyPaymentData() {
        Payment payment = new Payment("pay-123", "BANK", bankData);
        Map<String, String> emptyData = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setPaymentData(emptyData);
        });
    }

    @Test
    void testSetValidPaymentData() {
        Payment payment = new Payment("pay-123", "BANK", bankData);

        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put("bankName", "New Bank");
        newPaymentData.put("referenceCode", "NEW987654");

        payment.setPaymentData(newPaymentData);

        assertSame(newPaymentData, payment.getPaymentData());
        assertEquals("New Bank", payment.getPaymentData().get("bankName"));
        assertEquals("NEW987654", payment.getPaymentData().get("referenceCode"));
    }

    @Test
    void testValidVoucherCodePayment() {
        // Create valid voucher code that meets all requirements
        Map<String, String> correctVoucherData = new HashMap<>();
        correctVoucherData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("pay-123", "VOUCHER_CODE", correctVoucherData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodePayment() {
        Payment payment = new Payment("pay-123", "VOUCHER_CODE", invalidVoucherData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeTooShort() {
        Map<String, String> shortVoucherData = new HashMap<>();
        shortVoucherData.put("voucherCode", "ESHOP123456789");

        Payment payment = new Payment("pay-123", "VOUCHER_CODE", shortVoucherData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeWrongPrefix() {
        Map<String, String> wrongPrefixVoucher = new HashMap<>();
        wrongPrefixVoucher.put("voucherCode", "WRONG1234ABCD5678");

        Payment payment = new Payment("pay-123", "VOUCHER_CODE", wrongPrefixVoucher);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeTooFewNumbers() {
        Map<String, String> tooFewNumbers = new HashMap<>();
        tooFewNumbers.put("voucherCode", "ESHOPABCDEFGHIJKL");

        Payment payment = new Payment("pay-123", "VOUCHER_CODE", tooFewNumbers);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testValidBankTransfer() {
        Payment payment = new Payment("pay-123", "BANK", bankData);
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    }

    @Test
    void testInvalidBankTransferEmptyBankName() {
        Map<String, String> emptyBankName = new HashMap<>();
        emptyBankName.put("bankName", "");
        emptyBankName.put("referenceCode", "REF123456");

        Payment payment = new Payment("pay-123", "BANK", emptyBankName);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testInvalidBankTransferNullReferenceCode() {
        Map<String, String> nullRefCode = new HashMap<>();
        nullRefCode.put("bankName", "Test Bank");
        nullRefCode.put("referenceCode", null);

        Payment payment = new Payment("pay-123", "BANK", nullRefCode);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testDifferentPaymentMethods() {
        // Create valid voucher code that meets all requirements
        Map<String, String> correctVoucherData = new HashMap<>();
        correctVoucherData.put("voucherCode", "ESHOP1234ABC5678");

        Payment bankPayment = new Payment("pay-123", "BANK", bankData);
        Payment voucherPayment = new Payment("pay-456", "VOUCHER_CODE", correctVoucherData);

        assertEquals("BANK", bankPayment.getMethod());
        assertEquals(PaymentStatus.PENDING.getValue(), bankPayment.getStatus());
        assertEquals("VOUCHER_CODE", voucherPayment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), voucherPayment.getStatus());
    }

    @Test
    void testUpdateVoucherCodeToValid() {
        Payment payment = new Payment("pay-123", "VOUCHER_CODE", invalidVoucherData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());

        // Create valid voucher code that meets all requirements
        Map<String, String> correctVoucherData = new HashMap<>();
        correctVoucherData.put("voucherCode", "ESHOP1234ABC5678");

        payment.setPaymentData(correctVoucherData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testUpdateBankDataToInvalid() {
        Payment payment = new Payment("pay-123", "BANK", bankData);
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());

        Map<String, String> invalidBankData = new HashMap<>();
        invalidBankData.put("bankName", "Test Bank");
        invalidBankData.put("referenceCode", "");

        payment.setPaymentData(invalidBankData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}