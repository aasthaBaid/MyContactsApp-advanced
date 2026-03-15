
package com.search;

import com.contact.Contact;

public class PhoneCriteria implements SearchCriteria {
	private String keyword;

	public PhoneCriteria(String keyword) {
		this.keyword = keyword;
	}


	@Override
	public boolean matches(Contact contact) {
		return contact.getPhoneNumbers().stream()
				.anyMatch(p -> p.toString().contains(keyword));
	}

}
