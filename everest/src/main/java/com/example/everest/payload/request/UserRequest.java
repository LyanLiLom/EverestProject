package com.example.everest.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.sql.Timestamp;

public class UserRequest {
    private int id;
    @NotBlank(message = "First name không được để trống")
    @Size(max = 50, message = "First name không được dài quá 50 ký tự")
    private String firstName;

    @NotBlank(message = "Last name không được để trống")
    @Size(max = 50, message = "Last name không được dài quá 50 ký tự")
    private String lastName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không được dài quá 100 ký tự")
    private String email;

    @NotBlank(message = "Password không được để trống")
    @Pattern(
            regexp = "^(?=.{8,})(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
            message = "Password phải có ít nhất 8 ký tự, chứa ít nhất một chữ cái, một số và một ký tự đặc biệt (@$!%*?&)"
    )
    private String password;

    @NotBlank(message = "Phone không được để trống")
    @Pattern(
            regexp = "^(\\+84|0)[3-9]\\d{8}$",
            message = "Phone không hợp lệ, vui lòng nhập số điện thoại Việt Nam hợp lệ"
    )
    private String phone;

    @NotBlank(message = "Role không được để trống")
    @Pattern(
            regexp = "^(ADMIN|USER|MANAGER)$",
            message = "Role không hợp lệ, chỉ được phép là ADMIN, USER, hoặc MANAGER"
    )
    private String role;

    @NotNull(message = "Status không được để trống")
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public enum Status{
        ACTIVE,
        LOCKED
    }
}
