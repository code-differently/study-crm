package com.codedifferently.studycrm.contacts.api.web;

public class CreateContactRequest {
    private String firstName;
    private String lastName;

    public CreateContactRequest() {
    }

    public CreateContactRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}