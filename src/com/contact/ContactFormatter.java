package com.contact;

public class ContactFormatter {
    private final ContactView view;

    public ContactFormatter(ContactView view) {
        this.view = view;
    }

    public String format() {
        return "=== Contact Information ===\n" + view.toString() + "===========================\n";
    }
}
