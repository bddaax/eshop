package enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {

    VOUCHER_CODE("VOUCHER_CODE"),
    BANK("BANK");

    private final String value;

    private PaymentMethod(String paymentMethod) {
        this.value = paymentMethod;
    }

    public static boolean contains(String param) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}