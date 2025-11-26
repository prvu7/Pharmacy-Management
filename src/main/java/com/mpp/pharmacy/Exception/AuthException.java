package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.LogType;

public class AuthException extends AppException {
    public AuthException(String message) {
        super(message, LogType.AUTH_ERROR);
    }
}