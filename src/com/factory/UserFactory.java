package com.factory;

import com.model.*;

public class UserFactory {
	// user factory to choose between free and premium user
    public static User createUser(String type, UserBuilder builder) {
        if(type.equalsIgnoreCase("free")) {
            return new FreeUser(builder);
        } else if(type.equalsIgnoreCase("premium")) {
            return new PremiumUser(builder);
        }
        throw new IllegalArgumentException("Invalid user type");
    }
}
