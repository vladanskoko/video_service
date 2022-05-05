package com.springboot.videoservice.controller;

import com.springboot.videoservice.model.Role;
import com.springboot.videoservice.service.impl.RoleServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final RoleServiceImpl roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public Role saveRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("{id}")
    public Role getRoleById(@PathVariable("id") Long roleId) {
        return roleService.getRoleById(roleId);
    }

    @PutMapping("{id}")
    public Role updateRole(@RequestBody Role role, @PathVariable("id") Long roleId) {
        return roleService.updateRole(role, roleId);
    }

    @DeleteMapping("{id}")
    void deleteRole(@PathVariable("id") Long roleId) {
        roleService.deleteRole(roleId);
    }
}
