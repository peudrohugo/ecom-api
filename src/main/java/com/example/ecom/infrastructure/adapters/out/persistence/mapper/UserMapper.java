package com.example.ecom.infrastructure.adapters.out.persistence.mapper;

import com.example.ecom.domain.model.User;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.UserData;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserData userData) {
        if (userData == null) {
            return null;
        }
        return User.builder()
                .id(userData.getId())
                .email(userData.getEmail())
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .createdAt(userData.getCreatedAt())
                .build();
    }

    public UserData toUserData(User user) {
        if (user == null) {
            return null;
        }
        return new UserData(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getCreatedAt()
        );
    }
}
