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

    public void setName(String name) { this.name = name; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setPreferences(String preferences) { /* store preferences */ }

    @Override
    public String toString() {
        return "User [email=" + email + ", name=" + name + ", phone=" + phoneNumber + "]";
    }
}
