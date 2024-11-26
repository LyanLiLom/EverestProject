package com.example.everest.dto;

import com.example.everest.entity.UserEntity;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserDTO implements Serializable {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;
    private UserEntity.Status status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    public UserDTO(int id, String email, String firstName, String lastName, String phone,
                   String role, UserEntity.Status status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public UserEntity.Status getStatus() {
        return status;
    }

    public void setStatus(UserEntity.Status status) {
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

    public enum Status {
        ACTIVE,
        LOCKED
    }
}
