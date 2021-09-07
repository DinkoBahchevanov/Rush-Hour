package com.example.module2.util;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ExceptionUtil {

    private ExceptionUtil() {

    }

    public static Map<String, Object> getBody(Exception ex, HttpStatus httpStatus) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", httpStatus.value());
        body.put("message", ex.getMessage());


        return body;
    }
}
