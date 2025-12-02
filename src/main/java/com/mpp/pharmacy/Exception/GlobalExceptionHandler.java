package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.CustomLogger;
import com.mpp.pharmacy.Loggers.LogType;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final CustomLogger logger = CustomLogger.getInstance();

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        logger.warn(LogType.INVENTORY, "Resource not found: " + e.getMessage());
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException e, WebRequest request) {
        logger.error(LogType.DATABASE_ERROR, "Database error occurred", e);
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, Object>> handleAuthException(AuthException ex) {
        log.error("Authentication Error: {}", ex.getMessage());

        return ResponseEntity.status(401).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "errorType", "AUTHENTICATION_ERROR",
                "message", ex.getMessage()
        ));
    }

    // fallback handler for unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpectedException(Exception e, WebRequest request) {
        logger.error(LogType.UNEXPECTED_ERROR, "An unexpected error occurred", e);
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request){
        logger.warn(LogType.INVALID_REQUEST, "Illegal argument: " + e.getMessage());
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e, WebRequest request) {
        logger.error(LogType.GENERAL_INFO, "Null pointer exception occurred", e);
        return ResponseEntity.status(500).body("A null pointer exception occurred: " + e.getMessage());
    }
}
