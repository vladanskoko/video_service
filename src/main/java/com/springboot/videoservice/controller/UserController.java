package com.springboot.videoservice.controller;

import com.springboot.videoservice.model.User;
import com.springboot.videoservice.service.impl.ChannelServiceImpl;
import com.springboot.videoservice.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") Long userId) {
        User existingUser = getUserById(userId);
        existingUser.setName(user.getName());
        return userService.saveUser(existingUser);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
    }
}
