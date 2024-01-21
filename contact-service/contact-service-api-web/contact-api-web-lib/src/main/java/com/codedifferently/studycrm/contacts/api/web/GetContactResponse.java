package com.codedifferently.studycrm.contacts.api.web;

public class GetContactResponse {
    private long contactId;
    private String firstName;
    private String lastName;

    public GetContactResponse() {
    }

    public GetContactResponse(long contactId, String firstName, String lastName) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
