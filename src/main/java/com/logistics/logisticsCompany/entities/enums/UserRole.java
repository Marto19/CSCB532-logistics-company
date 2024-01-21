package com.logistics.logisticsCompany.entities.enums;

import com.logistics.logisticsCompany.entities.users.User;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_role", nullable = false, length = 255)
    private String userRole;

    ////////////////////////////////////making  relationships   //////////////////////////////
    @ManyToMany
    private Set<User> userList;

    public UserRole(String userRole) {
        this.userRole = userRole;
    }

    public UserRole() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
