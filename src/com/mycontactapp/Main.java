package com.mycontactapp;

import java.util.*;

import com.model.*;
import com.factory.UserFactory;
import com.auth.*;
import com.session.SessionManager;
import com.tag.Tag;
import com.tag.TagFactory;
import com.profile.*;
import com.search.*;
import com.contact.*;
import com.persistence.FilePersistence;

public class Main {
	private static Map<String, User> userStore = new HashMap<>();
	private static List<Contact> contacts = new ArrayList<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// Load persisted data
		userStore = FilePersistence.loadUsers();
		contacts = FilePersistence.loadContacts();

		boolean running = true;
		while(running) {
			System.out.println("\n=== MyContacts Menu ===");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Exit");
			System.out.print("Choose an option: ");
			String choice = sc.nextLine();

			switch(choice) {
			case "1":
				registerUser(sc);
				break;
			case "2":
				loginUser(sc);
				break;
			case "3":
				FilePersistence.saveUsers(userStore);
				FilePersistence.saveContacts(contacts);
				running = false;
				System.out.println("Exiting MyContacts. Goodbye!");
				break;
			default:
				System.out.println("Invalid choice. Try again.");
			}
		}
		sc.close();
	}

	// === Registration ===
	private static void registerUser(Scanner sc) {
		try {
			System.out.println("\n=== User Registration ===");
			System.out.print("Enter email: ");
			String email = sc.nextLine();

			System.out.print("Enter password: ");
			String password = sc.nextLine();

			System.out.print("Enter name: ");
			String name = sc.nextLine();

			System.out.print("Enter phone number: ");
			String phone = sc.nextLine();

			System.out.print("Enter user type (free/premium): ");
			String type = sc.nextLine();

			UserBuilder builder = new UserBuilder()
					.setEmail(email)
					.setPassword(password)
					.setName(name)
					.setPhoneNumber(phone);

			if(type.equalsIgnoreCase("premium")) {
				System.out.print("Enter subscription plan: ");
				String plan = sc.nextLine();
				builder.setSubscriptionPlan(plan);
			}

			User user = UserFactory.createUser(type, builder);
			userStore.put(user.getEmail(), user);
			System.out.println("Registration successful: " + user);

			FilePersistence.saveUsers(userStore); // persist immediately
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// === Login ===
	private static void loginUser(Scanner sc) {
		System.out.println("\n=== User Login ===");
		System.out.print("Enter email: ");
		String loginEmail = sc.nextLine();
		System.out.print("Enter password: ");
		String loginPassword = sc.nextLine();

		AuthenticationStrategy auth = new BasicAuth(userStore);
		Optional<User> loggedIn = auth.authenticate(loginEmail, loginPassword);

		if(loggedIn.isPresent()) {
			SessionManager.getInstance().login(loggedIn.get());
			System.out.println("Welcome, " + loggedIn.get().getName() + "!");

			// Show submenu
			userSubMenu(sc, loggedIn.get());

		} else {
			System.out.println("Login failed. Invalid credentials.");
		}
	}

	// === User Submenu (Profile + Contacts) ===
	private static void userSubMenu(Scanner sc, User user) {
		boolean managing = true;
		while(managing) {
			System.out.println("\n=== User Menu ===");
			System.out.println("1. Profile Management");
			System.out.println("2. Contact Management");
			System.out.println("3. Logout");
			System.out.print("Choose an option: ");
			String choice = sc.nextLine();

			switch(choice) {
			case "1":
				profileMenu(sc, user);
				break;
			case "2":
				contactMenu(sc);
				break;
			case "3":
				SessionManager.getInstance().logout();
				managing = false;
				break;
			default:
				System.out.println("Invalid choice.");
			}
		}
	}

	// === Profile Management ===
	private static void profileMenu(Scanner sc, User user) {
		boolean managing = true;
		while(managing) {
			System.out.println("\n=== Profile Management ===");
			System.out.println("1. Change Name");
			System.out.println("2. Change Phone Number");
			System.out.println("3. Change Password");
			System.out.println("4. Change Preferences");
			System.out.println("5. Back");
			System.out.print("Choose an option: ");
			String choice = sc.nextLine();

			switch(choice) {
			case "1":
				System.out.print("Enter new name: ");
				new ChangeNameCommand(sc.nextLine()).execute(user);
				break;
			case "2":
				System.out.print("Enter new phone number: ");
				new ChangePhoneCommand(sc.nextLine()).execute(user);
				break;
			case "3":
				System.out.print("Enter new password: ");
				new ChangePasswordCommand(sc.nextLine()).execute(user);
				break;
			case "4":
				System.out.print("Enter new preferences: ");
				new ChangePreferencesCommand(sc.nextLine()).execute(user);
				break;
			case "5":
				managing = false;
				break;
			default:
				System.out.println("Invalid choice.");
			}
			FilePersistence.saveUsers(userStore); // persist changes immediately
		}
	}

	// === Contact Management ===
	// Contact menu with bulk operations
	private static void contactMenu(Scanner sc) {
		boolean managing = true;
		while(managing) {
			System.out.println("\n=== Contact Management ===");
			System.out.println("1. Create Contact");
			System.out.println("2. View All Contacts");
			System.out.println("3. View Contact Details");
			System.out.println("4. Edit Contact");
			System.out.println("5. Delete Contact");
			System.out.println("6. Bulk Operations"); // new option
			System.out.println("7. Search Contacts");
			System.out.println("8. Advanced Filtering");
			System.out.println("9. Manage Tags");
			System.out.println("10. Back");
			System.out.print("Choose an option: ");
			String choice = sc.nextLine();

			switch(choice) {
			case "1": createContact(sc); break;
			case "2": viewContacts(); break;
			case "3": viewContactDetails(sc); break;
			case "4": editContact(sc); break;
			case "5": deleteContact(sc); break;
			case "6": bulkOperations(sc); break; // call new method
			case "7": searchContacts(sc); break;
			case "8": advancedFiltering(sc); break;
			case "9": tagMenu(sc); break;
			case "10": managing = false; break; 

			default: System.out.println("Invalid choice.");
			}
		}
	}



	private static void tagMenu(Scanner sc) {
		boolean managing = true;

		while(managing) {
			System.out.println("\n=== Tag Management ===");
			System.out.println("1. Create Tag");
			System.out.println("2. Assign Tag to Contact");
			System.out.println("3. Remove Tag from Contact");
			System.out.println("4. View Contact Tags");
			System.out.println("5. Back");
			System.out.print("Choose: ");
			String choice = sc.nextLine();

			switch(choice) {
			case "1": createTag(sc); break;
			case "2": assignTag(sc); break;
			case "3": removeTag(sc); break;
			case "4": viewContactTags(sc); break;
			case "5": managing = false; break;
			default: System.out.println("Invalid choice.");
			}
		}
	}


	private static void viewContactTags(Scanner sc) {
		System.out.print("Contact name: ");
		String name = sc.nextLine();

		Optional<Contact> match = contacts.stream()
				.filter(c -> c.getName().equalsIgnoreCase(name))
				.findFirst();

		if(match.isEmpty()) {
			System.out.println("Contact not found.");
			return;
		}

		Contact contact = match.get();

		System.out.println("Tags: " + contact.getTags());
	}

	private static void createTag(Scanner sc) {
		System.out.print("Enter tag name: ");
		String name = sc.nextLine();

		try {
			Tag t = TagFactory.getTag(name);
			System.out.println("Tag created: " + t);
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void assignTag(Scanner sc) {
		System.out.print("Contact name: ");
		String name = sc.nextLine();

		Optional<Contact> match = contacts.stream()
				.filter(c -> c.getName().equalsIgnoreCase(name))
				.findFirst();

		if(match.isEmpty()) {
			System.out.println("Contact not found.");
			return;
		}

		Contact contact = match.get();

		System.out.print("Enter tag: ");
		String tagName = sc.nextLine();

		Tag t = TagFactory.getTag(tagName);
		contact.addTag(t);

		FilePersistence.saveContacts(contacts);

		System.out.println("Tag assigned: " + t);
	}

	private static void removeTag(Scanner sc) {
		System.out.print("Contact name: ");
		String name = sc.nextLine();

		Optional<Contact> match = contacts.stream()
				.filter(c -> c.getName().equalsIgnoreCase(name))
				.findFirst();

		if(match.isEmpty()) {
			System.out.println("Contact not found.");
			return;
		}

		Contact contact = match.get();

		System.out.print("Enter tag name to remove: ");
		String tagName = sc.nextLine();

		Tag t = TagFactory.getTag(tagName);

		if(contact.getTags().contains(t)) {
			contact.removeTag(t);
			FilePersistence.saveContacts(contacts);
			System.out.println("Tag removed.");
		} else {
			System.out.println("Contact does not have this tag.");
		}
	}

	private static void advancedFiltering(Scanner sc) {

		CompositeFilter filterGroup = new CompositeFilter();

		System.out.println("\n=== Advanced Filtering ===");

		// DATE RANGE FILTER
		System.out.print("Start date (YYYY-MM-DD) or blank: ");
		String startStr = sc.nextLine();

		System.out.print("End date (YYYY-MM-DD) or blank: ");
		String endStr = sc.nextLine();

		if(!startStr.isEmpty() && !endStr.isEmpty()) {
			filterGroup.addFilter(new DateFilter(
					java.time.LocalDate.parse(startStr).atStartOfDay(),
					java.time.LocalDate.parse(endStr).atTime(23, 59)
					));
		}

		// FREQUENT FILTER
		System.out.print("Show top N frequently contacted (or blank): ");
		String limitStr = sc.nextLine();

		if(!limitStr.isEmpty()) {
			int limit = Integer.parseInt(limitStr);
			filterGroup.addFilter(new FrequentFilter(limit));
		}

		// APPLY FILTERS
		List<Contact> result = filterGroup.apply(contacts);

		// DISPLAY
		System.out.println("\n=== Filter Results ===");
		if(result.isEmpty()) System.out.println("No contacts match filters.");
		else result.forEach(System.out::println);
	}

	private static void searchContacts(Scanner sc) {
		System.out.println("\n=== Search Contacts ===");
		System.out.print("Enter name keyword (or blank): ");
		String nameKey = sc.nextLine();

		System.out.print("Enter phone keyword (or blank): ");
		String phoneKey = sc.nextLine();

		System.out.print("Enter email keyword (or blank): ");
		String emailKey = sc.nextLine();

		FilterHandler chain = null;
		FilterHandler head = null;

		if(!nameKey.isEmpty()) {
			head = new SearchFilter(new NameCriteria(nameKey));
			chain = head;
		}
		if(!phoneKey.isEmpty()) {
			FilterHandler f = new SearchFilter(new PhoneCriteria(phoneKey));
			if(head == null) head = f;
			else chain = chain.setNext(f);
		}
		if(!emailKey.isEmpty()) {
			FilterHandler f = new SearchFilter(new EmailCriteria(emailKey));
			if(head == null) head = f;
			else chain = chain.setNext(f);
		}

		if(head == null) {
			System.out.println("No filters applied.");
			return;
		}

		List<Contact> results = head.filter(contacts);

		System.out.println("\n=== Search Results ===");
		if(results.isEmpty()) {
			System.out.println("No matching contacts found.");
		} else {
			results.forEach(System.out::println);
		}
	}

	// Bulk operations method
	private static void bulkOperations(Scanner sc) {
		System.out.println("\n=== Bulk Operations ===");
		System.out.print("Enter keyword to filter contacts (e.g., 'gmail'): ");
		String keyword = sc.nextLine();

		// Filter contacts using Streams API + lambda
		List<Contact> selected = contacts.stream()
				.filter(c -> c.getEmails().toString().contains(keyword))
				.toList();

		if(selected.isEmpty()) {
			System.out.println("No contacts match filter.");
			return;
		}

		// Wrap in ContactGroup (Composite)
		ContactGroup group = new ContactGroup(selected);

		System.out.println("Bulk actions:");
		System.out.println("1. Delete all");
		System.out.println("2. Export all");
		System.out.print("Choose: ");
		String choice = sc.nextLine();

		switch(choice) {
		case "1":
			group.deleteAll(contacts); // delete selected contacts
			FilePersistence.saveContacts(contacts); // persist changes
			System.out.println("Deleted " + selected.size() + " contacts.");
			break;
		case "2":
			String exportData = group.exportAll(); // export selected contacts
			System.out.println("Exported contacts:\n" + exportData);
			break;
		default:
			System.out.println("Invalid choice.");
		}
	}


	// Method to delete a contact
	private static void deleteContact(Scanner sc) {
		System.out.print("Enter contact name to delete: ");
		String name = sc.nextLine();

		Optional<Contact> match = contacts.stream()
				.filter(c -> c.getName().equalsIgnoreCase(name))
				.findFirst();

		if(match.isPresent()) {
			Contact contact = match.get();

			// Confirmation dialog
			System.out.print("Are you sure you want to delete " + contact.getName() + "? (y/n): ");
			String confirm = sc.nextLine();

			if(confirm.equalsIgnoreCase("y")) {
				// Execute delete command
				DeleteContactCommand cmd = new DeleteContactCommand(contact);
				cmd.execute(contacts);

				// Notify observers (if any)
				// Example: log deletion
				System.out.println("Contact deleted: " + contact);

				// Persist changes
				FilePersistence.saveContacts(contacts);
			} else {
				System.out.println("Deletion cancelled.");
			}
		} else {
			System.out.println("No contact found with name: " + name);
		}
	}

	private static void editContact(Scanner sc) {
		System.out.print("Enter contact name to edit: ");
		String name = sc.nextLine();

		Optional<Contact> match = contacts.stream()
				.filter(c -> c.getName().equalsIgnoreCase(name))
				.findFirst();

		if(match.isPresent()) {
			Contact contact = match.get();

			System.out.println("Editing " + contact.getName());
			System.out.println("1. Change Name");
			System.out.println("2. Change Phone");
			System.out.println("3. Change Email");
			System.out.print("Choose field: ");
			String choice = sc.nextLine();

			try {
				ContactMemento oldState = null;
				switch(choice) {
				case "1":
					System.out.print("Enter new name: ");
					String newName = sc.nextLine();
					oldState = new EditNameCommand(newName).execute(contact);
					break;
					// Similarly implement EditPhoneCommand, EditEmailCommand
				}
				System.out.println("Contact updated: " + contact);
				FilePersistence.saveContacts(contacts); // persist immediately

				// Optionally allow undo
				System.out.print("Undo change? (y/n): ");
				if(sc.nextLine().equalsIgnoreCase("y") && oldState != null) {
					oldState.restore(contact);
					System.out.println("Undo complete. Contact restored: " + contact);
					FilePersistence.saveContacts(contacts);
				}
			} catch(Exception e) {
				System.out.println("Error editing contact: " + e.getMessage());
			}
		} else {
			System.out.println("No contact found with name: " + name);
		}
	}


	private static void viewContactDetails(Scanner sc) {
		System.out.print("Enter contact name to view details: ");
		String name = sc.nextLine();

		Optional<Contact> match = contacts.stream()
				.filter(c -> c.getName().equalsIgnoreCase(name))
				.findFirst();

		if(match.isPresent()) {
			ContactView view = new ContactView(match.get());
			ContactFormatter formatter = new ContactFormatter(view);
			System.out.println(formatter.format());
		} else {
			System.out.println("No contact found with name: " + name);
		}
	}

	private static void createContact(Scanner sc) {
		System.out.println("\n=== Create Contact ===");
		System.out.print("Enter contact type (person/organization): ");
		String type = sc.nextLine();

		ContactBuilder builder = new ContactBuilder();
		System.out.print("Enter name: ");
		builder.setName(sc.nextLine());

		System.out.print("Enter phone number (or blank to skip): ");
		String phone = sc.nextLine();
		if(!phone.isEmpty()) builder.addPhoneNumber(phone);

		System.out.print("Enter email (or blank to skip): ");
		String email = sc.nextLine();
		if(!email.isEmpty()) builder.addEmail(email);

		if(type.equalsIgnoreCase("organization")) {
			System.out.print("Enter organization name: ");
			builder.setOrganizationName(sc.nextLine());
		}

		Contact contact = ContactFactory.createContact(type, builder);
		contacts.add(contact);
		System.out.println("Contact created: " + contact);

		FilePersistence.saveContacts(contacts); // persist immediately
	}

	private static void viewContacts() {
		System.out.println("\n=== All Contacts ===");
		for(Contact c : contacts) {
			System.out.println(c);
		}
	}
}
