package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import enums.PaymentMethod;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private String orderId; // Tambahan field untuk mengaitkan dengan Order

    public Payment(String id, String orderId, String method, Map<String, String> paymentData) {
        this.id = id;
        this.orderId = orderId;
        this.setMethod(method);
        this.setPaymentData(paymentData);
        this.status = PaymentStatus.PENDING.getValue();
    }

    public Payment(String id, String orderId, String method, Map<String, String> paymentData, String status) {
        this.id = id;
        this.orderId = orderId;
        this.setMethod(method);
        this.paymentData = paymentData;
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }
    }

    public void setMethod(String method) {
        for (PaymentMethod pm : PaymentMethod.values()) {
            if (pm.name().equalsIgnoreCase(method)) {
                this.method = pm.name();
                return;
            }
        }
        throw new IllegalArgumentException("Invalid payment method: " + method);
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data cannot be empty");
        } else {
            this.paymentData = paymentData;
        }
    }
}