package com.simplecrm.contacts.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class ContactsController {
    @GetMapping("/contacts")
    public ResponseEntity<String> getContactsResponse() {
        return ResponseEntity.ok("Hello, World Again (thrice)!!");
    }
}
