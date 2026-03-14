package com.auth;

import java.util.Optional;
import com.model.User;

public interface AuthenticationStrategy {
    Optional<User> authenticate(String email, String password);
}
