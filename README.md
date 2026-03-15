# MyContactsApp-advanced

## UC‑01: User Registration
Users can create an account by entering their email, password, name, and phone number.
We use a User class to store these details, and a UserBuilder to build user objects step by step.

## UC‑02: User Login
Registered users can log in using their email and password.
We use a small login system that checks saved users and starts a session if the credentials are correct.

## UC‑03: User Profile Management
After logging in, users can update their:

Name
Phone number
Password
Preferences

Each change is handled by a separate command class so the code stays clean.

## UC‑04: Create Contact
Users can create two types of contacts:

Person
Organization

A contact can have:

Multiple phone numbers
Multiple email addresses

A builder is used here too, so contacts are created cleanly and safely.

## UC‑05: View Contact Details
Users can see all details of any saved contact.
The information is shown neatly using a formatter class.

## UC‑06: Edit Contact
Users can change a contact’s details, like the name.
Before editing, the app saves the old version so users can undo a change if needed.

## UC‑07: Delete Contact
Users can delete contacts.
The system asks “Are you sure?” to avoid accidental deletion.
Once deleted, the contact is removed from the list and saved.

## UC‑08: Bulk Operations
Users can select multiple contacts at once and:

Delete them together
Export them together

A group class handles bulk actions so we can treat a group exactly like a single contact.

## UC‑09: Search Contacts
Users can search for contacts using:

Name
Phone
Email

The search is flexible, and users can combine multiple conditions.

## UC‑10: Advanced Filtering
Users can filter contacts based on:

Date created (from‑to)
Most frequently contacted

These filters can be combined, so users can narrow down exactly who they want.

## UC‑11: Create and Manage Tags
Users can create their own categories, like:

Family
Work
Friends
VIP

Tags are reusable, so the same “Family” tag isn’t created twice.

## UC‑12: Apply Tags to Contacts
Users can:

Assign a single tag
Assign multiple tags at once
Remove tags

Tags help group and organize contacts better.
Each contact has its own set of tags.
