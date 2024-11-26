package com.example.everest.service;

import com.example.everest.dto.UserDTO;
import com.example.everest.entity.UserEntity;
import com.example.everest.payload.request.UserRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUser(String token,int page,int size);
    UserDTO convertDTO(UserEntity userEntity);
    void insertUser(UserRequest userRequest);
    void updateUser(UserRequest userRequest);
    void deleteUser(int id);
    Page<UserDTO> getPageUser(int page, int size);
}
