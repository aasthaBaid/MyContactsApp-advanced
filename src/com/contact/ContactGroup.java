package com.contact;

import java.util.*;

// Composite class to treat groups of contacts like a single unit
public class ContactGroup {
    private final List<Contact> contacts;

    public ContactGroup(List<Contact> contacts) {
        this.contacts = new ArrayList<>(contacts); // defensive copy
    }

    // Bulk delete: remove all selected contacts from master list
    public void deleteAll(List<Contact> masterList) {
        contacts.forEach(masterList::remove);
    }

    // Bulk export: return formatted string of all selected contacts
    public String exportAll() {
        StringBuilder sb = new StringBuilder();
        contacts.forEach(c -> sb.append(c.toString()).append("\n"));
        return sb.toString();
    }
}
