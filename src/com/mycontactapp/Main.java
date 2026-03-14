package com.mycontactapp;

import java.io.*;
import java.util.*;
import com.model.*;
import com.factory.UserFactory;
import com.auth.*;
import com.session.SessionManager;

public class Main {
    private static Map<String, User> userStore = new HashMap<>();
    private static final String FILE_NAME = "users.txt";


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadUsers(); // load users at startup

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
                    saveUsers(); // save before exit
                    running = false;
                    System.out.println("Exiting MyContacts. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        sc.close();
    }

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
            saveUsers();
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

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
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
    }

    // === Persistence Methods ===
    private static void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for(User user : userStore.values()) {
                if(user instanceof PremiumUser) {
                    PremiumUser p = (PremiumUser) user;
                    writer.write("premium," + user.getEmail() + "," + user.getPasswordHash() + "," +
                                 user.getName() + "," + user.getPhoneNumber() + "," + p.getSubscriptionPlan());
                } else {
                    writer.write("free," + user.getEmail() + "," + user.getPasswordHash() + "," +
                                 user.getName() + "," + user.getPhoneNumber());
                }
                writer.newLine();
            }
            System.out.println("Users saved to " + FILE_NAME);
        } catch(IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private static void loadUsers() {
        File file = new File(FILE_NAME);
        if(!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                UserBuilder builder = new UserBuilder()
                        .setEmail(parts[1])
                        .setName(parts[3])
                        .setPhoneNumber(parts[4]);

                // Inject stored password hash directly
                java.lang.reflect.Field f = UserBuilder.class.getDeclaredField("passwordHash");
                f.setAccessible(true);
                f.set(builder, parts[2]);

                if(type.equalsIgnoreCase("premium")) {
                    builder.setSubscriptionPlan(parts[5]);
                }
                User user = UserFactory.createUser(type, builder);
                userStore.put(user.getEmail(), user);
            }
            System.out.println("Users loaded from " + FILE_NAME);
        } catch(Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }
}
