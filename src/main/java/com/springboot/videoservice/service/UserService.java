package com.springboot.videoservice.service;

import com.springboot.videoservice.model.Role;
import com.springboot.videoservice.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(User user, Long id);
    void deleteUser(Long id);
}
