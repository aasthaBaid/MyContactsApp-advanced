package com.contact;

public class EditNameCommand implements EditCommand {
    private final String newName;

    public EditNameCommand(String newName) {
        this.newName = newName;
    }

    @Override
    public ContactMemento execute(Contact contact) {
        // Save old state
        ContactMemento memento = new ContactMemento(contact);

        // Validation before change
        if(newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        // Apply change
        contact.setName(newName);
        return memento;
    }
}
