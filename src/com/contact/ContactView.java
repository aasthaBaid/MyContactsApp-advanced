package com.contact;

import java.util.Optional;

public final class ContactView {
    private final Contact contact;

    public ContactView(Contact contact) {
        this.contact = contact;
    }

    public String getName() { return contact.getName(); }
    public Optional<String> getOrganizationName() {
        if(contact instanceof OrganizationContact) {
            return Optional.of(((OrganizationContact) contact).getOrganizationName());
        }
        return Optional.empty();
    }
    public String getPhones() { return contact.getPhoneNumbers().toString(); }
    public String getEmails() { return contact.getEmails().toString(); }
    public String getCreatedAt() { return contact.getCreatedAt().toString(); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Contact Details:\n");
        sb.append("Name: ").append(getName()).append("\n");
        getOrganizationName().ifPresent(org -> sb.append("Organization: ").append(org).append("\n"));
        sb.append("Phones: ").append(getPhones()).append("\n");
        sb.append("Emails: ").append(getEmails()).append("\n");
        sb.append("Created At: ").append(getCreatedAt()).append("\n");
        return sb.toString();
    }
}
