package com.contact;

public class ContactFactory {
    public static Contact createContact(String type, ContactBuilder builder) {
        if(type.equalsIgnoreCase("person")) {
            return new PersonContact(builder);
        } else if(type.equalsIgnoreCase("organization")) {
            return new OrganizationContact(builder);
        }
        throw new IllegalArgumentException("Invalid contact type");
    }
}
