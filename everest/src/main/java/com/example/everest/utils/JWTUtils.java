package com.example.everest.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtils {
    @Value("${key.token.jwt}")
    private String strKey;

    public String createToken(String subject, String issuer, String role){
        long now = System.currentTimeMillis();
        long expiry = now + 30L * 24 * 60 * 60 * 1000;
        //tạo token
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        return Jwts.builder()
                .subject(subject)
                .issuer(issuer)
                .issuedAt(new Date(now))
                .expiration(new Date(expiry))
                .claim("role",role)
                .signWith(secretKey)
                .compact();
    }

    public Claims decryptTokenToClaims(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        return  Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
