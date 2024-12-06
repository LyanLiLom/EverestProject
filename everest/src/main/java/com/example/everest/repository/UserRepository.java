package com.example.everest.repository;

import com.example.everest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndPhone(String email, String phone);
}
