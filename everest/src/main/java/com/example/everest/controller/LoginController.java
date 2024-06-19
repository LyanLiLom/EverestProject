package com.example.everest.controller;

import com.example.everest.exception.InsertException;
import com.example.everest.payload.request.LoginRequest;
import com.example.everest.payload.request.RegisterRequest;
import com.example.everest.payload.response.BaseResponse;
import com.example.everest.service.imp.LoginServiceImp;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginServiceImp loginServiceImp;
    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

//        SecretKey secretKey = Jwts.SIG.HS256.key().build();
//        String key = Encoders.BASE64.encode(secretKey.getEncoded());
//        System.out.println("Kiểm tra key" + key);



        String email = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        try {
            String token = loginServiceImp.checkLogin(email, password, response);
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
            boolean isRegistered = loginServiceImp.checkRegister(registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    registerRequest.getFirstname(),
                    registerRequest.getLastname(),
                    registerRequest.getPhone());
            System.out.println(isRegistered);
            if (isRegistered == true) {
                return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("User registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (InsertException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
