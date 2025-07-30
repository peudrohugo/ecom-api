package com.example.ecom.infrastructure.adapters.out.persistence.jpa.repository;

import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByEmail(String email);
}
