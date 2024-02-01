package com.codedifferently.studycrm.contacts.web;

import com.codedifferently.studycrm.contacts.api.web.CreateContactRequest;
import com.codedifferently.studycrm.contacts.api.web.CreateContactResponse;
import com.codedifferently.studycrm.contacts.api.web.GetContactResponse;
import com.codedifferently.studycrm.contacts.api.web.GetContactsResponse;
import com.codedifferently.studycrm.contacts.domain.Contact;
import com.codedifferently.studycrm.contacts.domain.ContactService;
import com.codedifferently.studycrm.contacts.domain.ContactRepository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class ContactsController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public CreateContactResponse createContact(@RequestBody CreateContactRequest createContactRequest) {
        Contact contact = contactService.createContact(createContactRequest.getFirstName(),
                createContactRequest.getLastName());
        return new CreateContactResponse(contact.getId());
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public ResponseEntity<GetContactsResponse> getAll() {
        return ResponseEntity
                .ok(new GetContactsResponse(StreamSupport.stream(contactRepository.findAll().spliterator(), false)
                        .map(c -> new GetContactResponse(c.getId(), c.getFirstName(), c.getLastName()))
                        .collect(Collectors.toList())));
    }

    @RequestMapping(value = "/contacts/{contactId}", method = RequestMethod.GET)
    public ResponseEntity<GetContactResponse> getContact(@PathVariable("contactId") UUID contactId) {
        return contactRepository
                .findById(contactId)
                .map(c -> new ResponseEntity<>(new GetContactResponse(c.getId(), c.getFirstName(), c.getFirstName()),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
