package com.example.everest.service;

import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {
    String checkLogin(String username, String password, HttpServletResponse response);
    boolean checkRegister(String email, String password, String firstname, String lastname, String phone);
}
