package com.codedifferently.studycrm.contacts.api.web;

public class CreateContactResponse {
    private Long contactId;

    public CreateContactResponse() {
    }

    public CreateContactResponse(Long contactId) {
        this.contactId = contactId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
}