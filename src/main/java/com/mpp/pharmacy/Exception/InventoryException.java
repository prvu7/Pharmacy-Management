package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.LogType;

public class InventoryException extends AppException {
    public InventoryException(String message) {
        super(message, LogType.INVENTORY);
    }
}