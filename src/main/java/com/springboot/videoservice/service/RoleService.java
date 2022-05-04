package com.springboot.videoservice.service;

import com.springboot.videoservice.model.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role user);
    List<Role> getAllRoles();
    Role getRoleById(Long id);
    Role updateRole(Role role, Long id);
    void deleteRole(Long id);
}
