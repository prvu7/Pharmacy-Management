package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.LogType;

public class InvalidRequestException extends AppException {
    public InvalidRequestException(String msg) {
        super(msg, LogType.INVALID_REQUEST);
    }
}