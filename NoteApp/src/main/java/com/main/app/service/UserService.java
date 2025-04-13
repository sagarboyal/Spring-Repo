package com.main.app.service;

import com.main.app.dto.UserDTO;
import com.main.app.model.User;
import java.util.List;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    UserDTO getUserById(Long id);
}
