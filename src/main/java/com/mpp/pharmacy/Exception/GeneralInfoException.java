package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.LogType;

public class GeneralInfoException extends AppException {
    public GeneralInfoException(String msg) {
        super(msg, LogType.GENERAL_INFO);
    }
}
