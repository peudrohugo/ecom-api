package com.example.ecom.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;

    @Builder
    private User(Long id, String email, String firstName, String lastName, LocalDateTime createdAt) {
        validateUserData(id, email, firstName, createdAt);

        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
    }

    private void validateUserData(Long id, String email, String firstName, LocalDateTime createdAt) {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("User ID must be positive if provided.");
        }
        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format or email is null/empty.");
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("Creation timestamp cannot be null.");
        }
    }

    public static User createNewUser(String email, String firstName, String lastName) {
        return User.builder()
                .id(null)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void updateEmail(String newEmail) {
        if (newEmail == null || !newEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format for update.");
        }
        this.email = newEmail;
    }
}
