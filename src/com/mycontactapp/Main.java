package com.mycontactapp;

import java.util.Scanner;
import com.model.UserBuilder;
import com.model.User;
import com.factory.UserFactory;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("=== User Registration ===");
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
            System.out.println("Registration successful: " + user);

        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
