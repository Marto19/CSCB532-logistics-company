package com.logistics.logisticsCompany.entities.enums;

import com.logistics.logisticsCompany.entities.users.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_role")
    private String userRole;

    ////////////////////////////////////making  relationships   //////////////////////////////
    @ManyToMany
    private List<User> userList;

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

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
