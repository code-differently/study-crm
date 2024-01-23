package com.codedifferently.studycrm.contacts.api.web;

import java.util.List;

public class GetContactsResponse {
    private List<GetContactResponse> contacts;

    public GetContactsResponse() {
    }

    public GetContactsResponse(List<GetContactResponse> contacts) {
        this.contacts = contacts;
    }

    public List<GetContactResponse> getContacts() {
        return contacts;
    }

    public void setContacts(List<GetContactResponse> contacts) {
        this.contacts = contacts;
    }
}
