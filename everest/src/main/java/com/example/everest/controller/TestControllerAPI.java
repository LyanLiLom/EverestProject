package com.example.everest.controller;

import com.example.everest.payload.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testAPI")
public class TestControllerAPI {
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> test() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData("GET TEST");

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> adminOnly() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData("ADMIN TEST");

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
