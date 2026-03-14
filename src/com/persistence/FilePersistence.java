package com.persistence;

import java.io.*;
import java.util.*;
import com.model.*;
import com.factory.UserFactory;
import com.contact.*;

public class FilePersistence {
    private static final String USER_FILE = "users.txt";
    private static final String CONTACT_FILE = "contacts.txt";

    // === USERS ===
    public static void saveUsers(Map<String, User> userStore) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
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
            System.out.println("Users saved to " + USER_FILE);
        } catch(IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public static Map<String, User> loadUsers() {
        Map<String, User> userStore = new HashMap<>();
        File file = new File(USER_FILE);
        if(!file.exists()) return userStore;

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
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
            System.out.println("Users loaded from " + USER_FILE);
        } catch(Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return userStore;
    }

    // === CONTACTS ===
    public static void saveContacts(List<Contact> contacts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONTACT_FILE))) {
            for(Contact c : contacts) {
                StringBuilder sb = new StringBuilder();
                if(c instanceof OrganizationContact) {
                    OrganizationContact org = (OrganizationContact) c;
                    sb.append("organization|").append(c.getName()).append("|");
                } else {
                    sb.append("person|").append(c.getName()).append("|");
                }

                // Phones
                List<PhoneNumber> phones = c.getPhoneNumbers();
                for(int i=0; i<phones.size(); i++) {
                    sb.append(phones.get(i));
                    if(i < phones.size()-1) sb.append(";");
                }
                sb.append("|");

                // Emails
                List<Email> emails = c.getEmails();
                for(int i=0; i<emails.size(); i++) {
                    sb.append(emails.get(i));
                    if(i < emails.size()-1) sb.append(";");
                }

                // Organization extra field
                if(c instanceof OrganizationContact) {
                    OrganizationContact org = (OrganizationContact) c;
                    sb.append("|").append(org.getOrganizationName());
                }

                writer.write(sb.toString());
                writer.newLine();
            }
            System.out.println("Contacts saved to " + CONTACT_FILE);
        } catch(IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    public static List<Contact> loadContacts() {
        List<Contact> contacts = new ArrayList<>();
        File file = new File(CONTACT_FILE);
        if(!file.exists()) return contacts;

        try (BufferedReader reader = new BufferedReader(new FileReader(CONTACT_FILE))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String type = parts[0];

                ContactBuilder builder = new ContactBuilder()
                        .setName(parts[1]);

                // Phones
                if(parts.length > 2 && !parts[2].isEmpty()) {
                    String[] phones = parts[2].split(";");
                    for(String ph : phones) builder.addPhoneNumber(ph);
                }

                // Emails
                if(parts.length > 3 && !parts[3].isEmpty()) {
                    String[] emails = parts[3].split(";");
                    for(String em : emails) builder.addEmail(em);
                }

                // Organization field
                if(type.equalsIgnoreCase("organization") && parts.length > 4) {
                    builder.setOrganizationName(parts[4]);
                }

                Contact contact = ContactFactory.createContact(type, builder);
                contacts.add(contact);
            }
            System.out.println("Contacts loaded from " + CONTACT_FILE);
        } catch(Exception e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
        return contacts;
    }
}
