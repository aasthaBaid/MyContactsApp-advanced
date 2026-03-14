package com.profile;

import com.model.User;

public class ChangePreferencesCommand implements ProfileUpdateCommand {
    private String newPreferences;

    public ChangePreferencesCommand(String newPreferences) {
        this.newPreferences = newPreferences;
    }

    @Override
    public void execute(User user) {
        user.setPreferences(newPreferences);
        System.out.println("Preferences updated to: " + newPreferences);
    }
}
