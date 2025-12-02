package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.LogType;

public class ValidationException extends AppException {
    public ValidationException(String message) {
        super(message, LogType.VALIDATION_ERROR);
    }
}