
package com.search;

import com.contact.Contact;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DateFilter implements AdvancedFilter {

    private LocalDateTime start;
    private LocalDateTime end;

    public DateFilter(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public List<Contact> apply(List<Contact> input) {
        return input.stream()
                .filter(c -> c.getCreatedAt().isAfter(start)
                          && c.getCreatedAt().isBefore(end))
                .collect(Collectors.toList());
    }
}
