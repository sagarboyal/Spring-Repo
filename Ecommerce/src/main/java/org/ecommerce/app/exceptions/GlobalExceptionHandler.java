package org.ecommerce.app.exceptions;

import org.ecommerce.app.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            response.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<APIResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                APIResponse.builder()
                        .message(ex.getMessage())
                        .status(false)
                        .build()
        );
    }

    @ExceptionHandler(value = {APIException.class})
    public ResponseEntity<APIResponse> resourceNotFoundExceptionHandler(APIException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                APIResponse.builder()
                        .message(ex.getMessage())
                        .status(false)
                        .build()
        );
    }
}
