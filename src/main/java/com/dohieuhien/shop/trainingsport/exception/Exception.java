package com.dohieuhien.shop.trainingsport.exception;

import feign.Feign;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.processing.FilerException;
import java.util.Map;

public class Exception {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeign(FeignException ex) {
        String upstream = ex.contentUTF8();
        if (ex.status() == 400 && upstream != null && !upstream.isBlank()) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(upstream);
        }
        HttpStatus status = ex.status() == 404 ? HttpStatus.NOT_FOUND : HttpStatus.BAD_GATEWAY;
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\":\"Dịch vụ training-sport lỗi hoặc không phản hồi\"}");
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<Map<String, Object>> handleDown(RetryableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("message", "Không kết nối được training-sport (chưa chạy hoặc timeout)"));
    }
}
