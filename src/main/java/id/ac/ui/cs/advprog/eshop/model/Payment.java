package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import enums.PaymentMethod;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.setMethod(method);
        this.setPaymentData(paymentData);
        this.status = PaymentStatus.PENDING.getValue();

        // Process payment based on method
        processPayment();
    }

    public Payment(String id, String method, Map<String, String> paymentData, String status) {
        this.id = id;
        this.setMethod(method);
        this.paymentData = paymentData;
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setMethod(String method) {
        for (PaymentMethod pm : PaymentMethod.values()) {
            if (pm.name().equalsIgnoreCase(method)) { // Case insensitive check
                this.method = pm.name(); // Store as enum name
                return;
            }
        }
        throw new IllegalArgumentException("Invalid payment method: " + method);
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.paymentData = paymentData;

            // Reprocess payment when data changes
            if (this.method != null) {
                processPayment();
            }
        }
    }

    private void processPayment() {
        System.out.println("DEBUG: Processing payment method: " + this.method);
        if (PaymentMethod.VOUCHER_CODE.name().equals(this.method)) {
            processVoucherPayment();
        } else if (PaymentMethod.BANK.name().equals(this.method)) {
            processBankPayment();
        }
    }

    private void processVoucherPayment() {
        String voucherCode = this.paymentData.get("voucherCode");
        System.out.println("DEBUG: Processing voucher code: " + voucherCode);
        if (isValidVoucherCode(voucherCode)) {
            this.status = PaymentStatus.SUCCESS.getValue();
        } else {
            this.status = PaymentStatus.REJECTED.getValue();
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

    private void processBankPayment() {
        String bankName = this.paymentData.get("bankName");
        String referenceCode = this.paymentData.get("referenceCode");

        if (bankName == null || bankName.isEmpty() || referenceCode == null || referenceCode.isEmpty()) {
            this.status = PaymentStatus.REJECTED.getValue();
        }
    }
}