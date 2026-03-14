package com.auth;

import java.util.Map;
import java.util.Optional;
import com.model.User;
import com.util.PasswordUtil;

public class BasicAuth implements AuthenticationStrategy {
    private Map<String, User> userStore; // simple in-memory store

    public BasicAuth(Map<String, User> userStore) {
        this.userStore = userStore;
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        User user = userStore.get(email);
        if(user != null) {
            String hashed = PasswordUtil.hashPassword(password);
            if(hashed.equals(user.getPasswordHash())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
