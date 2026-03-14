package com.contact;


import java.util.*;
// contact builder
public class ContactBuilder {
    private String name;
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();
    private List<Email> emails = new ArrayList<>();
    private String organizationName;

    public ContactBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ContactBuilder addPhoneNumber(String number) {
        this.phoneNumbers.add(new PhoneNumber(number));
        return this;
    }

    public ContactBuilder addEmail(String email) {
        this.emails.add(new Email(email));
        return this;
    }

    public ContactBuilder setOrganizationName(String orgName) {
        this.organizationName = orgName;
        return this;
    }

    public String getName() { return name; }
    public List<PhoneNumber> getPhoneNumbers() { return phoneNumbers; }
    public List<Email> getEmails() { return emails; }
    public String getOrganizationName() { return organizationName; }
}
