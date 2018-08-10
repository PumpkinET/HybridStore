package com.demo.hybridstore.com.hybridstore.model;

public class Login {
    String email;
    String password;
    String avatar;
    String name;

    public Login(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    public Login(String email, String password, String avatar, String name) {
        super();
        this.email = email;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

}
