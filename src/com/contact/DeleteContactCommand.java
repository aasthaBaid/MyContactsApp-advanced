package com.contact;

import java.util.List;

public class DeleteContactCommand {
    private final Contact contact;

    public DeleteContactCommand(Contact contact) {
        this.contact = contact;
    }

    // Execute deletion, return deleted contact for possible undo
    public Contact execute(List<Contact> contacts) {
        contacts.remove(contact); // hard delete
        return contact;
    }
}
