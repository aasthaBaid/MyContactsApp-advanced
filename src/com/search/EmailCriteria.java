
package com.search;

import com.contact.Contact;

public class EmailCriteria implements SearchCriteria {

    private String keyword;

    public EmailCriteria(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean matches(Contact contact) {
        return contact.getEmails().stream()
                .anyMatch(emailObj -> 
                        emailObj.getEmail().toLowerCase().contains(keyword)
                );
    }
}
