package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import enums.OrderStatus;
import enums.PaymentStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order testOrder;
    private Payment testPayment;
    private Map<String, String> validVoucherData;
    private Map<String, String> validBankData;
    private Map<String, String> invalidVoucherData;
    private Map<String, String> invalidBankData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        testOrder = new Order("order-123", products, System.currentTimeMillis(), "user1");

        validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP12345678ABC");

        invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "INVALID123");

        validBankData = new HashMap<>();
        validBankData.put("bankName", "BCA");
        validBankData.put("referenceCode", "REF123");

        invalidBankData = new HashMap<>();
        invalidBankData.put("bankName", "");
        invalidBankData.put("referenceCode", "REF123");

        testPayment = new Payment("payment-123", "order-123", "VOUCHER_CODE", validVoucherData, "PENDING");
    }

    @Test
    void testAddPaymentWithValidVoucher() {
        when(orderService.findById(anyString())).thenReturn(testOrder);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(testOrder, "VOUCHER_CODE", validVoucherData);

        verify(paymentRepository).save(any(Payment.class));
        verify(orderService).updateStatus(testOrder.getId(), OrderStatus.SUCCESS.getValue());

        assertEquals("VOUCHER_CODE", result.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(validVoucherData, result.getPaymentData());
        assertEquals(testOrder.getId(), result.getOrderId());
    }

    @Test
    void testAddPaymentWithInvalidVoucher() {
        when(orderService.findById(anyString())).thenReturn(testOrder);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(testOrder, "VOUCHER_CODE", invalidVoucherData);

        verify(paymentRepository).save(any(Payment.class));
        verify(orderService).updateStatus(testOrder.getId(), OrderStatus.FAILED.getValue());

        assertEquals("VOUCHER_CODE", result.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(invalidVoucherData, result.getPaymentData());
        assertEquals(testOrder.getId(), result.getOrderId());
    }

    @Test
    void testAddPaymentWithValidBankTransfer() {
        when(orderService.findById(anyString())).thenReturn(testOrder);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(testOrder, "BANK", validBankData);

        verify(paymentRepository).save(any(Payment.class));
        verify(orderService).updateStatus(testOrder.getId(), OrderStatus.SUCCESS.getValue());

        assertEquals("BANK", result.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(validBankData, result.getPaymentData());
        assertEquals(testOrder.getId(), result.getOrderId());
    }

    @Test
    void testAddPaymentWithInvalidBankTransfer() {
        when(orderService.findById(anyString())).thenReturn(testOrder);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(testOrder, "BANK", invalidBankData);

        verify(paymentRepository).save(any(Payment.class));
        verify(orderService).updateStatus(testOrder.getId(), OrderStatus.FAILED.getValue());

        assertEquals("BANK", result.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(invalidBankData, result.getPaymentData());
        assertEquals(testOrder.getId(), result.getOrderId());
    }

    @Test
    void testSetStatusToSuccess() {
        when(orderService.findById(anyString())).thenReturn(testOrder);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.setStatus(testPayment, PaymentStatus.SUCCESS.getValue());

        verify(paymentRepository).save(testPayment);
        verify(orderService).updateStatus(testOrder.getId(), OrderStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        when(orderService.findById(anyString())).thenReturn(testOrder);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.setStatus(testPayment, PaymentStatus.REJECTED.getValue());

        verify(paymentRepository).save(testPayment);
        verify(orderService).updateStatus(testOrder.getId(), OrderStatus.FAILED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testSetStatusToPending() {
        when(orderService.findById(anyString())).thenReturn(testOrder);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.setStatus(testPayment, PaymentStatus.PENDING.getValue());

        verify(paymentRepository).save(testPayment);
        verify(orderService, never()).updateStatus(anyString(), anyString());

        assertEquals(PaymentStatus.PENDING.getValue(), result.getStatus());
    }

    @Test
    void testGetPaymentExisting() {
        when(paymentRepository.findById("payment-123")).thenReturn(testPayment);

        Payment result = paymentService.getPayment("payment-123");

        verify(paymentRepository).findById("payment-123");

        assertEquals(testPayment, result);
    }

    @Test
    void testGetPaymentNonExisting() {
        when(paymentRepository.findById("non-existing")).thenReturn(null);

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            paymentService.getPayment("non-existing");
        });

        verify(paymentRepository).findById("non-existing");

        assertTrue(exception.getMessage().contains("Payment not found"));
    }

    @Test
    void testGetAllPayments() {
        List<Payment> expectedPayments = Arrays.asList(
                testPayment,
                new Payment("payment-456", "order-456", "BANK", validBankData)
        );

        when(paymentRepository.findAll()).thenReturn(expectedPayments);

        List<Payment> result = paymentService.getAllPayments();

        verify(paymentRepository).findAll();

        assertEquals(expectedPayments.size(), result.size());
        assertEquals(expectedPayments, result);
    }

    @Test
    void testVoucherValidation() {
        assertTrue(isValidVoucherCode("ESHOP12345678ABC"));
        assertFalse(isValidVoucherCode("SHOP123456789ABC"));
        assertFalse(isValidVoucherCode("ESHOP1234567"));
        assertFalse(isValidVoucherCode("ESHOP12345678ABCDEF"));
        assertFalse(isValidVoucherCode("ESHOP123ABC45DEF"));
        assertFalse(isValidVoucherCode(null));
    }

    private boolean isValidVoucherCode(String code) {
        if (code == null || code.length() != 16) {
            return false;
        }

        if (!code.startsWith("ESHOP")) {
            return false;
        }

        int digitCount = 0;
        for (char c : code.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }

        return digitCount == 8;
    }
}