package com.springboot.videoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
@Data
@AllArgsConstructor
public class UserRole {
    @EmbeddedId
    private UserRole.UserRoleId userRoleId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    public UserRole() {

    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        this.userRoleId = new UserRole.UserRoleId(user, role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistVideo)) return false;
        UserRole that = (UserRole) o;
        return getUserRoleId().equals(that.getUserRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserRoleId());
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "playlist=" + user.getId() +
                ", video=" + role.getId() +
                '}';
    }

    @Embeddable
    @Data
    public static class UserRoleId implements Serializable {
        private Long userId;
        private Long roleId;

        public UserRoleId() {

        }

        public UserRoleId(@NotNull User user, @NotNull Role role) {
            userId = user.getId();
            roleId = role.getId();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PlaylistVideo.PlaylistVideoId)) return false;
            UserRole.UserRoleId that = (UserRole.UserRoleId) o;
            return userId.equals(that.userId) && roleId.equals(that.roleId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, roleId);
        }
    }
}
