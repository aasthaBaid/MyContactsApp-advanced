package com.contact;


import java.time.LocalDateTime;
import java.util.*;

public abstract class Contact {
    private UUID id;
    private String name;
    private List<PhoneNumber> phoneNumbers;
    private List<Email> emails;
    private LocalDateTime createdAt;

    protected Contact(ContactBuilder builder) {
        this.id = UUID.randomUUID();
        this.name = builder.getName();
        this.phoneNumbers = builder.getPhoneNumbers();
        this.emails = builder.getEmails();
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public List<PhoneNumber> getPhoneNumbers() { return phoneNumbers; }
    public List<Email> getEmails() { return emails; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Contact [id=" + id + ", name=" + name + ", phones=" + phoneNumbers + ", emails=" + emails + "]";
    }
}
