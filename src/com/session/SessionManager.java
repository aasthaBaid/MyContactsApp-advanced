package com.session;

import com.model.User;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if(instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        this.currentUser = user;
        System.out.println("User logged in: " + user.getEmail());
    }

    public void logout() {
        System.out.println("User logged out: " + currentUser.getEmail());
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
