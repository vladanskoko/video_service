package com.springboot.videoservice.service.impl;

import com.springboot.videoservice.exception.ResourceNotFoundException;
import com.springboot.videoservice.model.Role;
import com.springboot.videoservice.model.User;
import com.springboot.videoservice.repository.RoleRepository;
import com.springboot.videoservice.repository.UserRepository;
import com.springboot.videoservice.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void addRoleToUser(Long userId, Long roleId) {
        User user = getUserById(userId);
        Role role = roleRepository.getById(roleId);
        user.getRoles().add(role);
        saveUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return user;
    }

    @Override
    public User updateUser(@NotNull User user, Long id) {
        User existingUser = getUserById(id);
        existingUser.setName(user.getName());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }
}
