package com.simplecrm.contacts.web;

import com.simplecrm.contacts.domain.Contact;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class ContactsController {
    @GetMapping("/contacts")
    public ResponseEntity<String> getContactsResponse() {
        Contact contact = new Contact("John", "Doe");
        return ResponseEntity.ok("Hello, World Again (thrice)!!");
    }
}
