package com.contact;

import java.util.*;

// Memento stores a snapshot of a Contact's state
public class ContactMemento {
    private final String name;
    private final List<PhoneNumber> phones;
    private final List<Email> emails;
    private final String organizationName;

    public ContactMemento(Contact contact) {
        this.name = contact.getName();
        this.phones = new ArrayList<>(contact.getPhoneNumbers()); // defensive copy
        this.emails = new ArrayList<>(contact.getEmails());       // defensive copy
        if(contact instanceof OrganizationContact) {
            this.organizationName = ((OrganizationContact) contact).getOrganizationName();
        } else {
            this.organizationName = null;
        }
    }

    // Restore state into a contact
    public void restore(Contact contact) {
        contact.setName(name);
        contact.setPhoneNumbers(new ArrayList<>(phones));
        contact.setEmails(new ArrayList<>(emails));
        if(contact instanceof OrganizationContact && organizationName != null) {
            ((OrganizationContact) contact).getOrganizationName();
        }
    }
}
