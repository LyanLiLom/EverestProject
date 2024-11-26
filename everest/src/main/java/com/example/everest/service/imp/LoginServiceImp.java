package com.example.everest.service.imp;

import com.example.everest.entity.UserEntity;
import com.example.everest.payload.response.RoleResponse;
import com.example.everest.repository.UserRepository;
import com.example.everest.utils.JWTUtils;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements com.example.everest.service.LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JWTUtils jwtUtils;

    private Gson gson = new Gson();
    @Override
    public String checkLogin(String username, String password, HttpServletResponse response) {
        String token = "";
        String issuer = "everestWeb";
            UserEntity userEntity = userRepository.findByEmail(username);
            if(passwordEncoder.matches(password,userEntity.getPassword())){
                RoleResponse roleResponse = new RoleResponse();
                roleResponse.setName(userEntity.getRole());

                String roleFE = userEntity.getRole();
                String role = gson.toJson(roleResponse);

                token = jwtUtils.createToken(username,issuer,role);

                System.out.println("token: " +  token);

                response.setHeader("Authorization",token);
                response.setHeader("Role", roleFE);

                response.getHeaderNames().forEach(headerName ->
                        System.out.println(headerName + ": " + response.getHeader(headerName)));
            }

        return token;
    }

    public boolean isEmailExists(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean checkRegister(String email, String password,
                                 String firstname, String lastname, String phone) {

        if(isEmailExists(email)){
            throw new RuntimeException("Email already registered.");
        }
        try {
            UserEntity user = new UserEntity();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            String passwordEncoderBcrypt = passwordEncoder.encode(password);
            user.setPassword(passwordEncoderBcrypt);
            user.setPhone(phone);
            user.setRole("ROLE_USER");
            UserEntity savedUser = userRepository.save(user);
            if (savedUser.getId() != 0) {
                return true;
            }
        }catch(RuntimeException e){
            throw new RuntimeException("Lỗi " + e.getMessage());
        }
        return false;
    }
}
