package com.springboot.videoservice.controller;

import com.springboot.videoservice.model.UserRole;
import com.springboot.videoservice.service.UserRoleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserRoleController {
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping("{userId}/role/{roleId}")
    public UserRole saveRoleToUser(@PathVariable("userId") Long userId,
                                   @PathVariable("roleId") Long roleId ) {
        return userRoleService.addRoleToUser(userId, roleId);
    }

    @DeleteMapping("{userId}/role/{roleId}")
    public void removeRoleFromUser(@PathVariable("userId") Long userId,
                                   @PathVariable("roleId") Long roleId) {
        userRoleService.removeRoleFromUser(userId, roleId);
    }
}
