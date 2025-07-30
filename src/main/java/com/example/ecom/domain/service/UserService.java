package com.example.ecom.domain.service;

import com.example.ecom.domain.model.User;
import com.example.ecom.domain.port.out.UserRepository;
import com.example.ecom.infrastructure.shared.exceptions.ExistingEmailException;
import com.example.ecom.infrastructure.shared.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createNewUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ExistingEmailException("User with email " + user.getEmail() + " already exists.");
        }

        return userRepository.save(user);
    }

    public User updateUserInformation(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User with ID " + user.getId() + " not found."));

        if (!existingUser.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new ExistingEmailException("Email " + user.getEmail() + " is already taken by another user.");
            }
        }

        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        return userRepository.save(existingUser);
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public void deleteUser(Long userId) {
        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found for deletion."));

        userRepository.delete(userToDelete.getId());
    }
}
