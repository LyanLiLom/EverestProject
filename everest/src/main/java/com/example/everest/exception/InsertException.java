package com.example.everest.exception;

public class InsertException extends RuntimeException{
    private String message;

    public InsertException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
