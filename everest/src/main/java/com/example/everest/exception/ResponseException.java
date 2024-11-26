package com.example.everest.exception;


import com.example.everest.payload.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResponseException {
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return buildResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "");
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(item -> {
            String field = ((FieldError) item).getField();
            String message = item.getDefaultMessage();
            errors.put(field, message);
        });
        return buildResponse("Validation failed", HttpStatus.BAD_REQUEST, errors);
    }

    private ResponseEntity<BaseResponse> buildResponse(String message, HttpStatus status, Object data) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(status.value());
        baseResponse.setMessage(message);
        baseResponse.setData(data);
        return new ResponseEntity<>(baseResponse, status);
    }

}


