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
    public ResponseEntity<?> handleException(Exception e){
        BaseResponse baseRespond = new BaseResponse();
        baseRespond.setStatusCode(500);
        baseRespond.setMessage(e.getMessage());
        baseRespond.setData("");
        return new ResponseEntity<>(baseRespond, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handValidException(MethodArgumentNotValidException e){
        Map<String,String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(item -> {
            String field = ((FieldError) item).getField();
            String message = item.getDefaultMessage();
            errors.put(field,message);
        });

        BaseResponse baseRespond = new BaseResponse();
        baseRespond.setStatusCode(400);
        baseRespond.setMessage("");
        baseRespond.setData(errors);

        return new ResponseEntity<>(baseRespond, HttpStatus.BAD_REQUEST);
    }
}


