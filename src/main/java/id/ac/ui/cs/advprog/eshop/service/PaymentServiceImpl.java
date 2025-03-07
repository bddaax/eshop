package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import enums.PaymentMethod;
import enums.PaymentStatus;
import enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.NoSuchElementException;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = UUID.randomUUID().toString();

        Payment payment = new Payment(paymentId, order.getId(), method, paymentData);

        if (PaymentMethod.VOUCHER_CODE.name().equals(method)) {
            processVoucherPayment(payment);
        } else if (PaymentMethod.BANK.name().equals(method)) {
            processBankPayment(payment);
        }

        paymentRepository.save(payment);

        updateOrderStatus(payment);

        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        paymentRepository.save(payment);

        updateOrderStatus(payment);

        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new NoSuchElementException("Payment not found with ID: " + paymentId);
        }
        return payment;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private void processVoucherPayment(Payment payment) {
        String voucherCode = payment.getPaymentData().get("voucherCode");

        if (isValidVoucherCode(voucherCode)) {
            payment.setStatus(PaymentStatus.SUCCESS.getValue());
        } else {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
        }
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

    private void processBankPayment(Payment payment) {
        String bankName = payment.getPaymentData().get("bankName");
        String referenceCode = payment.getPaymentData().get("referenceCode");

        if (bankName == null || bankName.isEmpty() || referenceCode == null || referenceCode.isEmpty()) {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
        } else {
            payment.setStatus(PaymentStatus.SUCCESS.getValue());
        }
    }

    private void updateOrderStatus(Payment payment) {
        try {
            Order order = orderService.findById(payment.getOrderId());

            if (PaymentStatus.SUCCESS.getValue().equals(payment.getStatus())) {
                orderService.updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());
            } else if (PaymentStatus.REJECTED.getValue().equals(payment.getStatus())) {
                orderService.updateStatus(order.getId(), OrderStatus.FAILED.getValue());
            }

        } catch (Exception e) {
            System.err.println("Failed to update order status: " + e.getMessage());
        }
    }
}