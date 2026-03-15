
package com.search;

import com.contact.Contact;
import java.util.List;

public interface AdvancedFilter {
    List<Contact> apply(List<Contact> input);
}
