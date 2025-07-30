package com.example.ecom.application.service;

import com.example.ecom.application.inbounds.CreateUserData;
import com.example.ecom.application.inbounds.UpdateUserData;
import com.example.ecom.application.outbounds.UserDTO;
import com.example.ecom.domain.model.User;
import com.example.ecom.domain.port.in.IUserService;
import com.example.ecom.domain.service.UserService;
import com.example.ecom.infrastructure.shared.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserApplicationService implements IUserService {
    private final UserService userService;

    @Override
    @Transactional
    public UserDTO createUser(CreateUserData data) {
        User newUser = User.createNewUser(
                data.email(),
                data.firstName(),
                data.lastName()
        );

        User createdUser = userService.createNewUser(newUser);

        return buildUserDTO(createdUser);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long userId, UpdateUserData data) {
        User existingUser = userService.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found for update."));

        existingUser.setEmail(data.email());
        existingUser.setFirstName(data.firstName());
        existingUser.setLastName(data.lastName());

        User updatedUser = userService.updateUserInformation(existingUser);

        return buildUserDTO(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long userId) {
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));

        return buildUserDTO(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    private UserDTO buildUserDTO(User user) {
        if(user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt().toString())
                .build();
    }
}
