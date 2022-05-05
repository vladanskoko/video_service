package com.springboot.videoservice.service;

import com.springboot.videoservice.model.UserRole;

public interface UserRoleService {
    UserRole addRoleToUser(Long userId, Long roleId);
    void removeRoleFromUser(Long userId, Long roleId);
}
