package com.springboot.videoservice.service.impl;

import com.springboot.videoservice.model.Role;
import com.springboot.videoservice.model.User;
import com.springboot.videoservice.model.UserRole;
import com.springboot.videoservice.repository.UserRoleRepository;
import com.springboot.videoservice.service.RoleService;
import com.springboot.videoservice.service.UserRoleService;
import com.springboot.videoservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserService userService;
    private final RoleService roleService;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserService userService, RoleService roleService) {
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserRole addRoleToUser(Long userId, Long roleId) {
        User user = userService.getUserById(userId);
        Role role = roleService.getRoleById(roleId);
        UserRole userRole = new UserRole(user, role);
        return userRoleRepository.save(userRole);
    }

    @Override
    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = userService.getUserById(userId);
        Role role = roleService.getRoleById(roleId);
        UserRole userRole = userRoleRepository.getUserRoleByUserAndRole(user, role);
        userRoleRepository.delete(userRole);
    }
}
