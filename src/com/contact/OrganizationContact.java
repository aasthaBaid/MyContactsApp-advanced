package com.contact;

public class OrganizationContact extends Contact {
    private String organizationName;

    public OrganizationContact(ContactBuilder builder) {
        super(builder);
        this.organizationName = builder.getOrganizationName();
    }

    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public String toString() {
        return super.toString() + " [Organization=" + organizationName + "]";
    }
}
