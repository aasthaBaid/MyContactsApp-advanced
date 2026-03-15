package com.contact;


import java.time.LocalDateTime;
import java.util.*;

import com.tag.Tag;

public abstract class Contact {
	private UUID id;
	private String name;
	private List<PhoneNumber> phoneNumbers;
	private List<Email> emails;
	private LocalDateTime createdAt;

	protected Contact(ContactBuilder builder) {
		this.id = UUID.randomUUID();
		this.name = builder.getName();
		this.phoneNumbers = builder.getPhoneNumbers();
		this.emails = builder.getEmails();
		this.createdAt = LocalDateTime.now();
	}

	public UUID getId() { return id; }
	public String getName() { return name; }
	public List<PhoneNumber> getPhoneNumbers() { return phoneNumbers; }
	public List<Email> getEmails() { return emails; }
	public LocalDateTime getCreatedAt() { return createdAt; }


	private int contactCount = 0;

	public int getContactCount() { return contactCount; }
	public void incrementContactCount() { contactCount++; }


	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}


	private Set<Tag> tags = new HashSet<>();

	public Set<Tag> getTags() { return tags; }

	public void addTag(Tag t) {
		tags.add(t);
	}

	public void removeTag(Tag t) {
		tags.remove(t);
	}
		@Override
		public String toString() {
			return "Contact [id=" + id + ", name=" + name + ", phones=" + phoneNumbers + ", emails=" + emails + "]";
		}
	}
