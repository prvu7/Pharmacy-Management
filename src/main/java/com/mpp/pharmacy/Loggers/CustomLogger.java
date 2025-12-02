package com.mpp.pharmacy.Loggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;
import org.slf4j.spi.LoggingEventBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomLogger {
    // The FQCN of this wrapper class.
    private static final String FQCN = CustomLogger.class.getName();
    private static final CustomLogger INSTANCE = new CustomLogger();

    private final Map<LogType, LocationAwareLogger> loggerMap = new ConcurrentHashMap<>();

    private CustomLogger() {}

    public static CustomLogger getInstance() {
        return INSTANCE;
    }

    private LocationAwareLogger getLogger(LogType type) {
        return loggerMap.computeIfAbsent(type, t -> {
            Logger logger = LoggerFactory.getLogger(t.name());
            if (!(logger instanceof LocationAwareLogger)) {
                throw new UnsupportedOperationException("The underlying logger for " + t.name() + " does not implement LocationAwareLogger.");
            }
            return (LocationAwareLogger) logger;
        });
    }

    private void log(LogType type, int level, String message, Throwable t) {
        getLogger(type).log(null, FQCN, level, message, null, t);
    }

    public void info(LogType type, String message) {
        log(type, LocationAwareLogger.INFO_INT, message, null);
    }

    public void error(LogType type, String message, Throwable throwable) {
        String fullMessage = message;
        if (throwable != null && throwable.getMessage() != null) {
            fullMessage += ": " + throwable.getMessage();
        }
        log(type, LocationAwareLogger.ERROR_INT, fullMessage, null);
    }

    public void warn(LogType type, String message) {
        log(type, LocationAwareLogger.WARN_INT, message, null);
    }

    public void debug(LogType type, String message) {
        log(type, LocationAwareLogger.DEBUG_INT, message, null);
    }
}
