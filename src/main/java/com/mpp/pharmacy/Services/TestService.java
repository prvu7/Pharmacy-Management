package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.Loggers.CustomLogger;
import com.mpp.pharmacy.Loggers.LogType;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private final CustomLogger logger = CustomLogger.getInstance();

    public String performTest() {
        logger.info(LogType.NOTIFICATION, "TestService: performTest method called.");
        return "Test successful!";
    }
}
