package com.example.everest.filter;

import com.example.everest.payload.response.RoleResponse;
import com.example.everest.utils.JWTUtils;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    private Gson gson = new Gson();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerAuthen = request.getHeader("Authorization");
        if(headerAuthen != null && headerAuthen.trim().length() > 0){
            String token = headerAuthen.substring(7);
            //Giải mã token
            Claims claims = jwtUtils.decryptTokenToClaims(token);

            if (claims != null){
                String username = claims.getSubject();
                String data = claims.get("role",String.class);

                RoleResponse role = gson.fromJson(data, RoleResponse.class);
                System.out.println("Kiểm tra jwt subject " + username + " - " + data +" - " + role.getName());

                List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
                authorityList.add(simpleGrantedAuthority);

                //Tạo chứng thực rỗng rằng đã hợp lệ
                UsernamePasswordAuthenticationToken authen = new
                        UsernamePasswordAuthenticationToken(username,null, authorityList);

                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authen);
            }
        }
        filterChain.doFilter(request,response);
    }
}
