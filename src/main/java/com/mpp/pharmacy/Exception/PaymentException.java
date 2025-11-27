package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.LogType;

public class PaymentException extends AppException {
    public PaymentException(String message) {
        super(message, LogType.PAYMENT_ERROR);
    }
}
