package com.mpp.pharmacy.Exception;

import com.mpp.pharmacy.Loggers.CustomLogger;
import com.mpp.pharmacy.Loggers.LogType;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web;
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
    public ResponseEntity<?> handleAuthException(AuthException e, WebRequest request) {
        logger.error(LogType.AUTH_ERROR, "Authentication error: " + e.getMessage(), null);
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(GeneralInfoException.class)
    public ResponseEntity<?> handleGeneralInfoException(GeneralInfoException e, WebRequest request) {
        logger.info(LogType.GENERAL_INFO, "General information: " + e.getMessage());
        return ResponseEntity.status(200).body(e.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> handleInvalidRequestException(InvalidRequestException e, WebRequest request) {
        logger.warn(LogType.INVALID_REQUEST, "Invalid request: " + e.getMessage());
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(InventoryException.class)
    public ResponseEntity<?> handleInventoryException(InventoryException e, WebRequest request) {
        logger.warn(LogType.INVENTORY, "Inventory error: " + e.getMessage());
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<?> handleNotificationException(NotificationException e, WebRequest request) {
        logger.warn(LogType.NOTIFICATION, "Notification: " + e.getMessage());
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<?> handleOrderException(OrderException e, WebRequest request) {
        logger.warn(LogType.ORDER_ERROR, "Order error: " + e.getMessage());
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException(PaymentException e, WebRequest request) {
            logger.warn(LogType.PAYMENT_ERROR, "Payment error: " + e.getMessage());
            return ResponseEntity.status(402).body(e.getMessage());
    }

    @ExceptionHandler(PersonException.class)
    public ResponseEntity<?> handlePersonException(PersonException e, WebRequest request) {
        logger.error(LogType.PERSON_ERROR, "Person error: " + e.getMessage(), e);
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(SaveException.class)
    public ResponseEntity<?> handleSaveException(SaveException e, WebRequest request) {
        logger.error(LogType.SAVE_ERROR, "Save error: " + e.getMessage(), e);
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e, WebRequest request) {
        logger.warn(LogType.VALIDATION_ERROR, "Validation error: " + e.getMessage());
        return ResponseEntity.status(400).body(e.getMessage());
    }

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
