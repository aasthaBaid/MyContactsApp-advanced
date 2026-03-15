
package com.search;

import com.contact.Contact;
import java.util.List;
import java.util.stream.Collectors;

public class SearchFilter extends FilterHandler {

    private SearchCriteria criteria;

    public SearchFilter(SearchCriteria criteria) {  
        this.criteria = criteria;
    }

   

	@Override
    public List<Contact> filter(List<Contact> contacts) {
        List<Contact> result = contacts.stream()
                .filter(c -> criteria.matches(c))
                .collect(Collectors.toList());

        if(next != null)
            return next.filter(result);

        return result;
    }
}
