package com.model;

public class PremiumUser extends User {
    private String subscriptionPlan;

    public PremiumUser(UserBuilder builder) {
        super(builder);
        this.subscriptionPlan = builder.getSubscriptionPlan();
    }

    public String getSubscriptionPlan() { return subscriptionPlan; }

    @Override
    public String toString() {
        return super.toString() + " [Premium Plan=" + subscriptionPlan + "]";
    }
}
