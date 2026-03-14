package com.profile;

import com.model.User;

public interface ProfileUpdateCommand {
    void execute(User user);
}
