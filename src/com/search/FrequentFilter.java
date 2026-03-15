package com.search;

import com.contact.Contact;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FrequentFilter implements AdvancedFilter {

    private int limit;

    public FrequentFilter(int limit) {
        this.limit = limit;
    }

    @Override
    public List<Contact> apply(List<Contact> input) {
        return input.stream()
                .sorted(Comparator.comparing(Contact::getContactCount).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
}
