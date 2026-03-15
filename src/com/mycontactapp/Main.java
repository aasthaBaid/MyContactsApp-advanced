
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

        userStore = FilePersistence.loadUsers();
        contacts = FilePersistence.loadContacts();

        boolean running = true;
        while (running) {

            System.out.println("\n=== MyContacts Menu ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            switch (sc.nextLine()) {
                case "1" -> registerUser(sc);
                case "2" -> loginUser(sc);
                case "3" -> {
                    FilePersistence.saveUsers(userStore);
                    FilePersistence.saveContacts(contacts);
                    running = false;
                    System.out.println("Exiting MyContacts. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        sc.close();
    }

    // ================= Registration =================
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

            if (type.equalsIgnoreCase("premium")) {
                System.out.print("Enter subscription plan: ");
                builder.setSubscriptionPlan(sc.nextLine());
            }

            User user = UserFactory.createUser(type, builder);
            userStore.put(user.getEmail(), user);

            System.out.println("Registration successful: " + user);
            FilePersistence.saveUsers(userStore);

        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    // ================= Login =================
    private static void loginUser(Scanner sc) {

        System.out.println("\n=== User Login ===");

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter password: ");
        String pass = sc.nextLine();

        AuthenticationStrategy auth = new BasicAuth(userStore);
        Optional<User> loggedIn = auth.authenticate(email, pass);

        if (loggedIn.isPresent()) {
            SessionManager.getInstance().login(loggedIn.get());
            System.out.println("Welcome, " + loggedIn.get().getName() + "!");
            userSubMenu(sc, loggedIn.get());
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    // ================= User Sub Menu =================
    private static void userSubMenu(Scanner sc, User user) {
        boolean loop = true;

        while (loop) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. Profile Management");
            System.out.println("2. Contact Management");
            System.out.println("3. Logout");
            System.out.print("Choose: ");

            switch (sc.nextLine()) {
                case "1" -> profileMenu(sc, user);
                case "2" -> contactMenu(sc);
                case "3" -> { SessionManager.getInstance().logout(); loop = false; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ================= Profile Menu =================
    private static void profileMenu(Scanner sc, User user) {

        boolean loop = true;

        while (loop) {
            System.out.println("\n=== Profile Management ===");
            System.out.println("1. Change Name");
            System.out.println("2. Change Phone");
            System.out.println("3. Change Password");
            System.out.println("4. Change Preferences");
            System.out.println("5. Back");
            System.out.print("Choose: ");

            switch (sc.nextLine()) {
                case "1" -> {
                    System.out.print("Enter new name: ");
                    new ChangeNameCommand(sc.nextLine()).execute(user);
                }
                case "2" -> {
                    System.out.print("Enter new phone: ");
                    new ChangePhoneCommand(sc.nextLine()).execute(user);
                }
                case "3" -> {
                    System.out.print("Enter new password: ");
                    new ChangePasswordCommand(sc.nextLine()).execute(user);
                }
                case "4" -> {
                    System.out.print("Enter preferences: ");
                    new ChangePreferencesCommand(sc.nextLine()).execute(user);
                }
                case "5" -> loop = false;
                default -> System.out.println("Invalid choice.");
            }

            FilePersistence.saveUsers(userStore);
        }
    }

    // ================= Contact Menu =================
    private static void contactMenu(Scanner sc) {
        boolean loop = true;

        while (loop) {

            System.out.println("\n=== Contact Management ===");
            System.out.println("1. Create Contact");
            System.out.println("2. View All Contacts");
            System.out.println("3. View Contact Details");
            System.out.println("4. Edit Contact");
            System.out.println("5. Delete Contact");
            System.out.println("6. Search Contacts");
            System.out.println("7. Manage Tags");
            System.out.println("8. Back");
            System.out.print("Choose: ");

            switch (sc.nextLine()) {
                case "1" -> createContact(sc);
                case "2" -> viewContacts();
                case "3" -> viewContactDetails(sc);
                case "4" -> editContact(sc);
                case "5" -> deleteContact(sc);
                case "6" -> searchContacts(sc);
                case "7" -> tagMenu(sc);
                case "8" -> loop = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ================= Tag Menu (UC‑11 + UC‑12) =================
    private static void tagMenu(Scanner sc) {

        boolean loop = true;

        while (loop) {
            System.out.println("\n=== Tag Management ===");
            System.out.println("1. Create Tag");
            System.out.println("2. Assign Single Tag");
            System.out.println("3. Remove Single Tag");
            System.out.println("4. Assign Multiple Tags");
            System.out.println("5. Remove Multiple Tags");
            System.out.println("6. View Contact Tags");
            System.out.println("7. Back");
            System.out.print("Choose: ");

            switch (sc.nextLine()) {
                case "1" -> createTag(sc);
                case "2" -> assignTag(sc);
                case "3" -> removeTag(sc);
                case "4" -> assignMultipleTags(sc);
                case "5" -> removeMultipleTags(sc);
                case "6" -> viewContactTags(sc);
                case "7" -> loop = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ================= Tag Methods (UC‑12) =================

    private static void createTag(Scanner sc) {
        System.out.print("Enter tag name: ");
        Tag t = TagFactory.getTag(sc.nextLine());
        System.out.println("Tag created: " + t);
    }

    private static void assignTag(Scanner sc) {
        Contact c = findContactByName(sc);
        if (c == null) return;

        System.out.print("Enter tag: ");
        Tag t = TagFactory.getTag(sc.nextLine());

        c.addTag(t);
        FilePersistence.saveContacts(contacts);

        System.out.println("Assigned tag: " + t);
    }

    private static void removeTag(Scanner sc) {
        Contact c = findContactByName(sc);
        if (c == null) return;

        System.out.print("Enter tag to remove: ");
        Tag t = TagFactory.getTag(sc.nextLine());

        if (c.getTags().contains(t)) {
            c.removeTag(t);
            FilePersistence.saveContacts(contacts);
            System.out.println("Tag removed.");
        } else {
            System.out.println("Contact does not have this tag.");
        }
    }

    private static void assignMultipleTags(Scanner sc) {
        Contact c = findContactByName(sc);
        if (c == null) return;

        System.out.print("Enter comma-separated tags: ");
        String[] tags = sc.nextLine().split(",");

        for (String t : tags) c.addTag(TagFactory.getTag(t.trim()));

        FilePersistence.saveContacts(contacts);

        System.out.println("Updated tags: " + c.getTags());
    }

    private static void removeMultipleTags(Scanner sc) {
        Contact c = findContactByName(sc);
        if (c == null) return;

        System.out.print("Enter comma-separated tags to remove: ");
        String[] tags = sc.nextLine().split(",");

        for (String t : tags) c.removeTag(TagFactory.getTag(t.trim()));

        FilePersistence.saveContacts(contacts);

        System.out.println("Updated tags: " + c.getTags());
    }

    private static void viewContactTags(Scanner sc) {
        Contact c = findContactByName(sc);
        if (c != null) {
            System.out.println("Tags: " + c.getTags());
        }
    }

    private static Contact findContactByName(Scanner sc) {
        System.out.print("Enter contact name: ");
        String name = sc.nextLine();

        return contacts.stream()
                .filter(ct -> ct.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Contact not found.");
                    return null;
                });
    }

    // ================= Search Contacts (UC‑09) =================
    private static void searchContacts(Scanner sc) {

        System.out.println("\n=== Search Contacts ===");

        System.out.print("Name keyword: ");
        String nameK = sc.nextLine();

        System.out.print("Phone keyword: ");
        String phoneK = sc.nextLine();

        System.out.print("Email keyword: ");
        String emailK = sc.nextLine();

        FilterHandler chain = null, head = null;

        if (!nameK.isEmpty()) {
            head = new SearchFilter(new NameCriteria(nameK));
            chain = head;
        }
        if (!phoneK.isEmpty()) {
            FilterHandler f = new SearchFilter(new PhoneCriteria(phoneK));
            if (head == null) head = f;
            else chain = chain.setNext(f);
        }
        if (!emailK.isEmpty()) {
            FilterHandler f = new SearchFilter(new EmailCriteria(emailK));
            if (head == null) head = f;
            else chain = chain.setNext(f);
        }

        if (head == null) {
            System.out.println("No filters applied.");
            return;
        }

        List<Contact> results = head.filter(contacts);

        System.out.println("\n=== Results ===");
        if (results.isEmpty()) System.out.println("No matching contacts.");
        else results.forEach(System.out::println);
    }

    // ================= CRUD =================

    private static void createContact(Scanner sc) {

        System.out.println("\n=== Create Contact ===");

        System.out.print("Type (person/organization): ");
        String type = sc.nextLine();

        ContactBuilder builder = new ContactBuilder();

        System.out.print("Name: ");
        builder.setName(sc.nextLine());

        System.out.print("Phone (optional): ");
        String phone = sc.nextLine();
        if (!phone.isEmpty()) builder.addPhoneNumber(phone);

        System.out.print("Email (optional): ");
        String email = sc.nextLine();
        if (!email.isEmpty()) builder.addEmail(email);

        if (type.equalsIgnoreCase("organization")) {
            System.out.print("Organization name: ");
            builder.setOrganizationName(sc.nextLine());
        }

        Contact c = ContactFactory.createContact(type, builder);
        contacts.add(c);

        FilePersistence.saveContacts(contacts);
        System.out.println("Contact created: " + c);
    }

    private static void deleteContact(Scanner sc) {

        Contact c = findContactByName(sc);
        if (c == null) return;

        System.out.print("Confirm delete? (y/n): ");
        if (sc.nextLine().equalsIgnoreCase("y")) {

            ContactGroup group = new ContactGroup(List.of(c));
            group.deleteAll(contacts);

            FilePersistence.saveContacts(contacts);
            System.out.println("Contact deleted.");
        }
    }

    private static void editContact(Scanner sc) {

        Contact c = findContactByName(sc);
        if (c == null) return;

        System.out.println("\n=== Edit Contact ===");
        System.out.println("1. Change Name");
        System.out.println("2. Change Phone (not implemented)");
        System.out.println("3. Change Email (not implemented)");
        System.out.print("Choose: ");

        try {
            ContactMemento oldS = null;

            if (sc.nextLine().equals("1")) {
                System.out.print("New name: ");
                oldS = new EditNameCommand(sc.nextLine()).execute(c);
            }

            FilePersistence.saveContacts(contacts);
            System.out.println("Updated: " + c);

            System.out.print("Undo change? (y/n): ");
            if (sc.nextLine().equalsIgnoreCase("y") && oldS != null) {
                oldS.restore(c);
                FilePersistence.saveContacts(contacts);
                System.out.println("Undo complete: " + c);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewContactDetails(Scanner sc) {

        Contact c = findContactByName(sc);
        if (c != null) {
            ContactFormatter f = new ContactFormatter(new ContactView(c));
            System.out.println(f.format());
        }
    }

    private static void viewContacts() {

        System.out.println("\n=== All Contacts ===");
        contacts.forEach(System.out::println);
    }
}
