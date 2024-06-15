package com.example.everest.service.imp;

import jakarta.servlet.http.HttpServletResponse;

public interface LoginServiceImp {
    String checkLogin(String username, String password, HttpServletResponse response);
    boolean checkRegister(String email, String password, String firstname, String lastname, String phone);
}
