package com.logistics.logisticsCompany.entities.enums;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logistics.logisticsCompany.entities.users.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The UserRole class is used to represent a user role entity.
 * It contains the id and user role of the user role.
 * Its supposed to be an enum but I made it an entity to be able to add more user roles in the future.
 */
@Entity
@Table(name = "user_role")
public class UserRole {
    /**
     * The id of the user role.
     * It is a unique identifier for the user role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The user role of the user role.
     */
    @Column(name = "user_role", nullable = false, length = 255)
    private String userRole;

    ////////////////////////////////////making  relationships   //////////////////////////////
    /**
     * The set of users of the user role.
     */
    @ManyToMany(mappedBy = "userRoleList")
    @JsonBackReference
    private Set<User> userList = new HashSet<>();

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
