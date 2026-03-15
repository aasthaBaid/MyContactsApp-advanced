
package com.search;

import com.contact.Contact;
import java.util.ArrayList;
import java.util.List;

public class CompositeFilter implements AdvancedFilter {

    private List<AdvancedFilter> filters = new ArrayList<>();

    public void addFilter(AdvancedFilter f) {
        filters.add(f);
    }

    @Override
    public List<Contact> apply(List<Contact> input) {
        List<Contact> result = new ArrayList<>(input);
        for (AdvancedFilter f : filters) {
            result = f.apply(result);
        }
        return result;
    }
}
