package com.codedifferently.studycrm.contacts.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codedifferently.studycrm.contacts.domain.Contact;
import com.codedifferently.studycrm.contacts.domain.ContactRepository;
import com.codedifferently.studycrm.contacts.domain.ContactService;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ContactsControllerTest {

  @Mock private ContactService contactService;

  @Mock private ContactRepository contactRepository;

  @InjectMocks private ContactsController contactsController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(contactsController).build();
  }

  @Test
  void testCreateContact_createsContact() throws Exception {
    // Arrange
    UUID contactId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    when(contactService.createContact("John", "Doe"))
        .thenReturn(Contact.builder().id(contactId).firstName("John").lastName("Doe").build());

    // Act
    mockMvc
        .perform(
            post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\"}"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"contactId\": \"123e4567-e89b-12d3-a456-426614174000\"}"));
  }

  @Test
  void testGetAll() throws Exception {
    // Arrange
    Contact contact1 = new Contact("John", "Doe");
    contact1.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    Contact contact2 = new Contact("Jane", "Smith");
    contact2.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174532"));
    when(contactRepository.findAll()).thenReturn(Arrays.asList(contact1, contact2));

    // Act
    mockMvc
        .perform(get("/contacts"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    "{"
                        + "\"contacts\": ["
                        + "{\"contactId\": \"123e4567-e89b-12d3-a456-426614174000\", \"firstName\": \"John\", \"lastName\": \"Doe\"},"
                        + "{\"contactId\": \"123e4567-e89b-12d3-a456-426614174532\", \"firstName\": \"Jane\", \"lastName\": \"Smith\"}"
                        + "]"
                        + "}"));
  }

  @Test
  void testGetContact() throws Exception {
    // Arrange
    UUID contactId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    Contact contact = Contact.builder().id(contactId).firstName("John").lastName("Doe").build();
    when(contactRepository.findById(contactId)).thenReturn(Optional.of(contact));

    // Act
    mockMvc
        .perform(get("/contacts/" + contactId))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    "{\"contactId\": \"123e4567-e89b-12d3-a456-426614174000\", \"firstName\": \"John\", \"lastName\": \"Doe\"}"));
  }
}
