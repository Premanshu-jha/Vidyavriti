package org.example.vidyavriti.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String,Object>> exceptionHandler(CustomException exception){
         Map<String,Object> response = new HashMap<>();
         response.put("status","FAILED");
         response.put("message",exception.getMessage());
         response.put("code",400);
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
