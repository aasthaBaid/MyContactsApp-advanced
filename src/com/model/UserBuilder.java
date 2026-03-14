package com.model;


import com.util.PasswordUtil;

public class UserBuilder {
    private String email;
    private String passwordHash;
    private String name;
    private String phoneNumber;
    private String subscriptionPlan;

    // email regex
    public UserBuilder setEmail(String email) {
        if(!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
        return this;
    }

    // password 
    public UserBuilder setPassword(String password) {
        if(password.length() < 6) {
            throw new IllegalArgumentException("Password too weak");
        }
        this.passwordHash = PasswordUtil.hashPassword(password);
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder setSubscriptionPlan(String plan) {
        this.subscriptionPlan = plan;
        return this;
    }

    // Getters for User class
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getSubscriptionPlan() { return subscriptionPlan; }
}
