package com.example.everest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @PostMapping("/product")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> productTable(){

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

//    @PostMapping("/user")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<?> userTable(){
//
//        return ResponseEntity.ok();
//    }

//    @PostMapping("/product")
//    public ResponseEntity<?> productTable(){
//
//        return ResponseEntity.ok();
//    }

}
