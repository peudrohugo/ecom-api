package com.example.ecom.domain.port.out;

import com.example.ecom.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    void delete(Long id);

    boolean existsById(Long id);
}
