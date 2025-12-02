package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.LogType;

public class OrderException extends AppException {
    public OrderException(String msg) {
        super(msg, LogType.ORDER_ERROR);
    }
}
