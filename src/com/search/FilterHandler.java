
package com.search;

import com.contact.Contact;
import java.util.List;

public abstract class FilterHandler {
    protected FilterHandler next;

    public FilterHandler setNext(FilterHandler next) {
        this.next = next;
        return next;
    }

    public abstract List<Contact> filter(List<Contact> contacts);
}
