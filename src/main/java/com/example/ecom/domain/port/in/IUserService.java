package com.example.ecom.domain.port.in;

import com.example.ecom.application.inbounds.CreateUserData;
import com.example.ecom.application.inbounds.UpdateUserData;
import com.example.ecom.application.outbounds.UserDTO;

public interface IUserService {
    UserDTO createUser(CreateUserData data);

    UserDTO updateUser(Long userId, UpdateUserData data);

    UserDTO getUserById(Long userId);

    void deleteUser(Long userId);
}
