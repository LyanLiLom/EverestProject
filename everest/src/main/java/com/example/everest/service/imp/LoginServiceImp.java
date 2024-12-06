package com.example.everest.service.imp;

import com.example.everest.entity.UserEntity;
import com.example.everest.payload.response.RoleResponse;
import com.example.everest.repository.UserRepository;
import com.example.everest.service.SendOTPtoEmailService;
import com.example.everest.utils.JWTUtils;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements com.example.everest.service.LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SendOTPtoEmailService sendOTPtoEmailService;

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
            user.setStatus(UserEntity.Status.ACTIVE);
            UserEntity savedUser = userRepository.save(user);
            if (savedUser.getId() != 0) {
                return true;
            }
        }catch(RuntimeException e){
            throw new RuntimeException("Lỗi " + e.getMessage());
        }
        return false;
    }

    public boolean checkEmailAndPhone(String email, String phone) {
            if (userRepository.existsByEmailAndPhone(email, phone)) {
                sendOTPtoEmailService.sendEmail(email);
                return true;
            } else {
                throw new RuntimeException("Invalid email or phone.");
            }
    }


    @Override
    public void resetPassword(String otpEmail, String newPassword) {
        int idUser;
        try{
            idUser = sendOTPtoEmailService.checkOTP(otpEmail);
            UserEntity user = userRepository.findById(idUser)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String passwordEncoded = passwordEncoder.encode(newPassword);
            user.setPassword(passwordEncoded);
            userRepository.save(user);
        }catch (RuntimeException e){
            throw new RuntimeException("Invalid OTP or OTP has expired.") ;
        }
    }
}
