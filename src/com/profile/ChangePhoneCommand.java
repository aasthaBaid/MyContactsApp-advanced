package com.profile;

import com.model.User;

public class ChangePhoneCommand implements ProfileUpdateCommand {
    private String newPhone;

    public ChangePhoneCommand(String newPhone) {
        this.newPhone = newPhone;
    }

    @Override
    public void execute(User user) {
        if(!newPhone.matches("\\d{10}")) {
            System.out.println("Invalid phone number format.");
            return;
        }
        user.setPhoneNumber(newPhone);
        System.out.println("Phone updated to: " + newPhone);
    }
}
