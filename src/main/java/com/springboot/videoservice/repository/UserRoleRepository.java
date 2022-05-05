package com.springboot.videoservice.repository;

import com.springboot.videoservice.model.Role;
import com.springboot.videoservice.model.User;
import com.springboot.videoservice.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRole.UserRoleId> {
    UserRole getUserRoleByUserAndRole(User user, Role role);
}
