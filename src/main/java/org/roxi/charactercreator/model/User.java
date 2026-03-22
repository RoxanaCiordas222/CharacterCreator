package org.roxi.charactercreator.model;

public class User {
    private String name;
    private String email;
    private String password;
    private Role role;

    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    public Role getRole() {
        return role;
    }
}