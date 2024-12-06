package com.example.everest.controller;

import com.example.everest.payload.request.LoginRequest;
import com.example.everest.payload.request.RegisterRequest;
import com.example.everest.payload.response.BaseResponse;
import com.example.everest.service.LoginService;
import com.example.everest.service.PasswordValidationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private PasswordValidationService passwordValidationService;


    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

//        SecretKey secretKey = Jwts.SIG.HS256.key().build();
//        String key = Encoders.BASE64.encode(secretKey.getEncoded());
//        System.out.println("Kiểm tra key" + key);



        String email = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        try {
            String token = loginService.checkLogin(email, password, response);
            System.out.println(token);
            if (!token.equals("")) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setStatusCode(HttpStatus.OK.value());
                baseResponse.setMessage("Login Success");
                baseResponse.setData(token);

                return ResponseEntity.ok(baseResponse);
            } else {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
                baseResponse.setMessage("Login Failed: Invalid email or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(baseResponse);
            }
        }  catch (RuntimeException e){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            baseResponse.setMessage("Login Failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(baseResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest){


        try {
            boolean isRegistered = loginService.checkRegister(registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    registerRequest.getFirstname(),
                    registerRequest.getLastname(),
                    registerRequest.getPhone());
            System.out.println(isRegistered);
            if (isRegistered) {
                return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("User registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestHeader(name = "email") String email,
                                            @RequestHeader(name = "phone") String phone){
        try {
            loginService.checkEmailAndPhone(email, phone);
            return ResponseEntity.ok("Email and phone were valid. OTP has been sent.");
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Authentication failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestHeader(name = "otp") String otp,
                                           @RequestHeader(name = "newPassword") String newPassword){
        if (!passwordValidationService.isValidPassword(newPassword)){
            throw new RuntimeException("Password must be at least 8 characters long, contain a letter, a number, and a special character.");
        }
        loginService.resetPassword(otp,newPassword);
        return ResponseEntity.ok("Reset Password successfully");
    }

}
