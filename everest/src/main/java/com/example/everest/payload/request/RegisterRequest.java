package com.example.everest.payload.request;

import jakarta.validation.constraints.*;

public class RegisterRequest {
    @NotBlank(message = "Email không được phép rỗng")
    @Email(message = "email phải đúng định dạng @gmail.com")
    private String email;
    @Size(min = 6,message = "Password không được ít hơn 6 kí tự")
    private String password;


    @Size(min = 9, message = "Số điện thoại phải ít nhất 9 số")
    private String phone;
    @NotBlank(message = "FirstName không được phép rỗng")
    private String firstname;
    @NotBlank(message = "LastName không được phép rỗng")
    private String lastname;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
