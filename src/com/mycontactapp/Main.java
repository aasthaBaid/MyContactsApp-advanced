package com.mycontactapp;

import java.util.*;
import com.model.*;
import com.factory.UserFactory;
import com.auth.*;
import com.session.SessionManager;
import com.profile.*;
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
    private static void contactMenu(Scanner sc) {
        boolean managing = true;
        while(managing) {
            System.out.println("\n=== Contact Management ===");
            System.out.println("1. Create Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch(choice) {
                case "1":
                    createContact(sc);
                    break;
                case "2":
                    viewContacts();
                    break;
                case "3":
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
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
