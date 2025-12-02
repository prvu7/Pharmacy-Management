package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.LogType;

public class SaveException extends AppException {
    public SaveException(String message) {
        super(message, LogType.SAVE_ERROR);
    }
}