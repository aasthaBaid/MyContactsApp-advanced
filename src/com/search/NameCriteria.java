
package com.search;

import com.contact.Contact;

public class NameCriteria implements SearchCriteria {
    private String keyword;

    public NameCriteria(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean matches(Contact contact) {
        return contact.getName() != null &&
                contact.getName().toLowerCase().contains(keyword);
    }
}
