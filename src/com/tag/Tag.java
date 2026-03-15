
package com.tag;

import com.contact.Contact;
import java.util.HashSet;
import java.util.Set;

public class Tag {

    private final String name;
    private Set<Contact> contacts = new HashSet<>();

    public Tag(String name) {
        if(name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Tag name cannot be empty");

        this.name = name.trim().toLowerCase();
    }

    public String getName() {
        return name;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact c) {
        contacts.add(c);
    }

    public void removeContact(Contact c) {
        contacts.remove(c);
    }

    @Override
    public String toString() {
        return "#" + name;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Tag)) return false;
        Tag t = (Tag) o;
        return name.equals(t.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
