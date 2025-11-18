package com.mpp.pharmacy.Loggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggingEventBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomLogger {
    // The FQCN of this wrapper class.
    private static final String FQCN = CustomLogger.class.getName();
    private static final CustomLogger INSTANCE = new CustomLogger();

    private final Map<LogType, Logger> loggerMap = new ConcurrentHashMap<>();

    private CustomLogger() {}

    public static CustomLogger getInstance() {
        return INSTANCE;
    }

    private LoggingEventBuilder getBuilder(LogType type, org.slf4j.event.Level level) {
        Logger logger = loggerMap.computeIfAbsent(type, t -> LoggerFactory.getLogger(t.name()));
        return logger.atLevel(level);
    }

    public void info(LogType type, String message) {
        getBuilder(type, org.slf4j.event.Level.INFO).log(message);
    }

    public void error(LogType type, String message, Throwable throwable) {
        String fullMessage = message + ": " + throwable.getMessage();
        getBuilder(type, org.slf4j.event.Level.ERROR).log(fullMessage);
    }

    public void warn(LogType type, String message) {
        getBuilder(type, org.slf4j.event.Level.WARN).log(message);
    }

    public void debug(LogType type, String message) {
        getBuilder(type, org.slf4j.event.Level.DEBUG).log(message);
    }
}
