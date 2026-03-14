package com.profile;

import com.model.User;
import com.util.PasswordUtil;

public class ChangePasswordCommand implements ProfileUpdateCommand {
    private String newPassword;

    public ChangePasswordCommand(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public void execute(User user) {
        if(newPassword.length() < 6) {
            System.out.println("Password too weak.");
            return;
        }
        String hashed = PasswordUtil.hashPassword(newPassword);
        user.setPasswordHash(hashed);
        System.out.println("Password updated successfully.");
    }
}
