package com.example.everest.service.imp;

import com.example.everest.entity.UserEntity;
import com.example.everest.repository.UserRepository;
import com.example.everest.service.SendOTPtoEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SendOTPtoEmailImp implements SendOTPtoEmailService {


    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String generateOTP() {
        String OTP = String.valueOf(new Random().nextInt(999999-100000+1) + 100000);
        return OTP;
    }

    @Override
    public void sendEmail(String email) {

            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                throw new RuntimeException("User not found with email: " + email);
            }

            String otp = generateOTP();
            redisTemplate.opsForValue().set(otp, String.valueOf(user.getId()), 2, TimeUnit.MINUTES);
            String subject = "Your OTP Code for Password Reset";
            String body = "Dear user, \n Your OTP code is: " + otp + " \n Thank you!";

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            try {
                mailSender.send(mailMessage);
            }catch (Exception e){
                System.out.println("Error sending OTP email: " + e.getMessage());
                throw new RuntimeException("Error sending OTP email: "+ e.getMessage());
            }
    }

    @Override
    public int checkOTP(String otp) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(otp))){
            String idUser = (String) redisTemplate.opsForValue().get(otp);
            assert idUser != null;
            return Integer.parseInt(idUser);
        }
        throw new RuntimeException("OTP not exists");
    }

}
