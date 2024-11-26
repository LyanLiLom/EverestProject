package com.example.everest.service.imp;

import com.example.everest.dto.UserDTO;
import com.example.everest.entity.UserEntity;
import com.example.everest.payload.request.UserRequest;
import com.example.everest.repository.UserRepository;
import com.example.everest.service.UserService;
import com.example.everest.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUser(String token,int page,int size) {
        String tokenDecryp = token.substring(7);
        if (!tokenDecryp.isEmpty()){
           Claims decrypToken = jwtUtils.decryptTokenToClaims(tokenDecryp);
           Object role = decrypToken.get("role");
           if (role.equals("{\"name\":\"ROLE_ADMIN\"}")){
               Page<UserDTO> listUserPage = getPageUser(page, size);
               return listUserPage.getContent();
           }else throw new RuntimeException("You do not have access");
        }
        throw new RuntimeException("Invalid token or token is empty");
    }

    @Override
    public UserDTO convertDTO(UserEntity userEntity) {
        return new UserDTO(userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getFirstname(),
                userEntity.getLastname(),
                userEntity.getPhone(),
                userEntity.getRole(),
                userEntity.getStatus(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt());
    }

    @Override
    public Page<UserDTO> getPageUser(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return userRepository.findAll(pageable).map(this::convertDTO);
    }

    @Transactional
    @Override
    public void insertUser(UserRequest userRequest) {
        try {
            UserEntity user = new UserEntity();
            user.setFirstname(userRequest.getFirstName());
            user.setLastname(userRequest.getLastName());

            if (!userRepository.existsByEmail(userRequest.getEmail())) {
                user.setEmail(userRequest.getEmail());
            }else throw new EmailAlreadyExistsException("Email already exists");

            String passwordEncoderBcrypt = passwordEncoder.encode(userRequest.getPassword());
            user.setPassword(passwordEncoderBcrypt);


            user.setPhone(userRequest.getPhone());
            user.setRole("ROLE_" + userRequest.getRole());

            if (userRequest.getStatus() == null) {
                user.setStatus(UserEntity.Status.ACTIVE);
            } else {
                try {
                    user.setStatus(UserEntity.Status.valueOf(userRequest.getStatus().name()));
                }catch (RuntimeException e){
                    throw new RuntimeException("Invalid status value provided" + e.getMessage());
                };
            }
            userRepository.save(user);
        }
        catch (RuntimeException e){
            throw new RuntimeException("Failed insert to user" + e.getMessage());
        };

    }

    @Transactional
    @Override
    public void updateUser(UserRequest userRequest) {
        int idUser = userRequest.getId();

        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found with id" + idUser));

        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setFirstname(userRequest.getFirstName());
        userEntity.setLastname(userRequest.getLastName());
        userEntity.setRole(userRequest.getRole());
        userEntity.setPhone(userRequest.getPhone());
        userEntity.setCreatedAt(userRequest.getCreatedAt());
        userEntity.setUpdatedAt(userRequest.getUpdatedAt());
        userEntity.setStatus(UserEntity.Status.valueOf(userRequest.getStatus().name()));

        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public void deleteUser(int id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        }else throw new RuntimeException("User not found with id: " + id);

    }

    public class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }


}
