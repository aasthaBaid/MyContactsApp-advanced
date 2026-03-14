package com.contact;

public class PhoneNumber {
    private String number;

    public PhoneNumber(String number) {
        if(!number.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.number = number;
    }

    @Override
    public String toString() { return number; }
}
