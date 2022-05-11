package com.springboot.videoservice.util;

import com.springboot.videoservice.model.Role;
import com.springboot.videoservice.model.User;
import com.springboot.videoservice.service.RoleService;
import com.springboot.videoservice.service.UserRoleService;
import com.springboot.videoservice.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DataInitialization implements ApplicationRunner {
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private RoleService roleService;
    private UserRoleService userRoleService;

    public DataInitialization(PasswordEncoder passwordEncoder, UserService userService,
                              RoleService roleService, UserRoleService userRoleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user1 = new User(1L, "vladan", "vladan@axiomq.com", "1234", new ArrayList<>());
        User user2 = new User(2L, "misa", "misa@axiomq.com", "1234", new ArrayList<>());
        User user3 = new User(3L, "stefan", "stefan@axiomq.com", "1234", new ArrayList<>());
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);

        Role role1 = new Role(1L, "ROLE_ADMIN", null);
        Role role2 = new Role(2L, "ROLE_USER", null);
        roleService.saveRole(role1);
        roleService.saveRole(role2);

        userRoleService.addRoleToUser(user1.getId(), role1.getId());
        userRoleService.addRoleToUser(user2.getId(), role2.getId());
        userRoleService.addRoleToUser(user3.getId(), role2.getId());
    }
}
