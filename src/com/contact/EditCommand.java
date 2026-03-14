package com.contact;

// Command interface for editing
public interface EditCommand {
    ContactMemento execute(Contact contact); // returns old state for undo
}
