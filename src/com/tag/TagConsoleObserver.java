
package com.tag;

import com.contact.Contact;

public class TagConsoleObserver implements TagObserver {
    @Override
    public void onTagUpdated(Contact contact) {
        System.out.println("[UI Refresh] Tags updated for " + contact.getName() 
            + ": " + contact.getTags());
    }
}
