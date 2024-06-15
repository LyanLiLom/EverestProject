package com.example.everest.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "Email không được phép rỗng")
    private String email;
    @Size(min = 3,message = "Password không được ít hơn 3 kí tự")
    private String password;

    @Size(max = 11, message = "Số điện thoại phải 11 kí tự")
    @Size(min = 11, message = "Số điện thoại phải 11 kí tự")
    private String phone;
    @NotBlank(message = "Email không được phép rỗng")
    private String firstname;
    @NotBlank(message = "Email không được phép rỗng")
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
