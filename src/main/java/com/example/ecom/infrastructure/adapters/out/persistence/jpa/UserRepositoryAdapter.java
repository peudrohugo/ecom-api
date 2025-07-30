package com.example.ecom.infrastructure.adapters.out.persistence.jpa;

import com.example.ecom.domain.model.User;
import com.example.ecom.domain.port.out.UserRepository;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.UserData;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.repository.UserJPARepository;
import com.example.ecom.infrastructure.adapters.out.persistence.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {
    private final UserJPARepository userRepository;
    private final UserMapper userMapper;

    public UserRepositoryAdapter(UserJPARepository userRepository,
                                 UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        UserData userData = userMapper.toUserData(user);
        UserData savedUserData = userRepository.save(userData);
        return userMapper.toUser(savedUserData);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserData> userDataOptional = userRepository.findById(id);
        return userDataOptional.map(userMapper::toUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserData> userDataOptional = userRepository.findByEmail(email);
        return userDataOptional.map(userMapper::toUser);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
