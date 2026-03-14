package com.profile;

import com.model.User;

public class ChangeNameCommand implements ProfileUpdateCommand{
	 private String newName;

	    public ChangeNameCommand(String newName) {
	        this.newName = newName;
	    }

	    @Override
	    public void execute(User user) {
	        user.setName(newName);
	        System.out.println("Name updated to: " + newName);
	    }

}
