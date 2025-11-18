package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.Loggers.CustomLogger;
import com.mpp.pharmacy.Loggers.LogType;
import com.mpp.pharmacy.Services.TestService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
@RequestMapping("/pharmacy")
public class TestController {

    private final TestService testService;

    private final CustomLogger customLogger = CustomLogger.getInstance();

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok(testService.performTest());
    }

    @GetMapping("ping")
    public ResponseEntity<String> getMethodName(@RequestParam String param) {

        if (param == null || param.isEmpty()) {
            customLogger.warn(LogType.INVALID_REQUEST, "Ping endpoint called with empty or null parameter");
            return ResponseEntity.badRequest().body("Parameter cannot be null or empty");
        }

        try {
            if (param.equals("invalid")) {
                throw new BadCredentialsException("Invalid username or password provided.");
            }
            return ResponseEntity.ok("Pong: " + param);
        } catch (BadCredentialsException e) {
            customLogger.error(LogType.AUTH_ERROR, "Simulated authentication failure", e);
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }
    }
    
}
