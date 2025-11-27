package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.LogType;

public class NotificationException extends AppException {
    public NotificationException(String msg) {
        super(msg, LogType.NOTIFICATION);
    }
}
