package com.search;


import com.contact.Contact;

public interface SearchCriteria {
    boolean matches(Contact contact);
}
