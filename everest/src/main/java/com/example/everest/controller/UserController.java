package com.example.everest.controller;

import com.example.everest.dto.UserDTO;
import com.example.everest.entity.UserEntity;
import com.example.everest.payload.request.ProductRequest;
import com.example.everest.payload.request.UserRequest;
import com.example.everest.payload.response.BaseResponse;
import com.example.everest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/table")
    public ResponseEntity<?> tableUser(@RequestHeader(name = "Authorization") String token,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){
        List<UserDTO> listUser = userService.getAllUser(token,page,size);
        BaseResponse baseResponse = new BaseResponse();
        if (!listUser.isEmpty()){
            baseResponse.setMessage("Success");
            baseResponse.setData(listUser);
            baseResponse.setStatusCode(200);
        }else {
            baseResponse.setStatusCode(204);
            baseResponse.setMessage("No users found");
            baseResponse.setData(Collections.emptyList());
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/insert")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> insterUser(@ModelAttribute @Valid UserRequest userRequest){
        try {
            userService.insertUser(userRequest);
            return new ResponseEntity<>("User insertd successfully", HttpStatus.CREATED);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@Valid @ModelAttribute UserRequest userRequest) {
        try {
            userService.updateUser(userRequest);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted sucessfully");
    }
}
