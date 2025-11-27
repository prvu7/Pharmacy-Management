package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.LogType;

public class ResourceNotFoundException extends AppException {
    public ResourceNotFoundException(String message) {
        super(message, LogType.INVALID_REQUEST);
    }
}
