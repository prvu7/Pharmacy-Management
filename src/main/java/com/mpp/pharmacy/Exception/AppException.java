package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.LogType;
import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException {

    private final LogType logType;

    public AppException(String message, LogType logType) {
        super(message);
        this.logType = logType;
    }
}