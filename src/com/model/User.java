package com.model;

public abstract class User {
    private String email;
    private String passwordHash;
    private String name;
    private String phoneNumber;

    
    protected User(UserBuilder builder) {
        this.email = builder.getEmail();
        this.passwordHash = builder.getPasswordHash();
        this.name = builder.getName();
        this.phoneNumber = builder.getPhoneNumber();
    }
    
    // getters and setters
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }

    @Override
    public String toString() {
        return "User [email=" + email + ", name=" + name + ", phone=" + phoneNumber + "]";
    }
}
