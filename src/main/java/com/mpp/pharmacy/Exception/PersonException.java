package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.LogType;

public class PersonException extends AppException {
    public PersonException(String message) {
        super(message, LogType.PERSON_ERROR);
    }
}
