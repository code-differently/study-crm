package com.codedifferently.studycrm.entities.web.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.codedifferently.studycrm.entities.domain.Entity;
import com.codedifferently.studycrm.entities.domain.EntityProperty;
import com.codedifferently.studycrm.entities.domain.EntityPropertyRepository;
import com.codedifferently.studycrm.entities.domain.EntityRepository;
import com.codedifferently.studycrm.entities.domain.EntityType;
import com.codedifferently.studycrm.entities.domain.EntityTypeRepository;
import com.codedifferently.studycrm.entities.domain.Property;
import com.codedifferently.studycrm.entities.domain.PropertyGroup;
import com.codedifferently.studycrm.entities.domain.PropertyGroupRepository;
import com.codedifferently.studycrm.entities.domain.PropertyRepository;
import com.codedifferently.studycrm.entities.domain.PropertyType;
import com.codedifferently.studycrm.entities.domain.PropertyTypeRepository;
import com.codedifferently.studycrm.entities.layout.domain.Container;
import com.codedifferently.studycrm.entities.layout.domain.ContainerType;
import com.codedifferently.studycrm.entities.layout.domain.GroupWidget;
import com.codedifferently.studycrm.entities.layout.domain.Layout;
import com.codedifferently.studycrm.entities.layout.domain.LayoutRepository;
import com.codedifferently.studycrm.entities.layout.domain.PropertyWidget;
import com.codedifferently.studycrm.entities.layout.domain.Widget;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component
@Order(1) // Run first
public class ExampleEntitiesInitializer implements CommandLineRunner {

  @Autowired private PropertyTypeRepository propertyTypeRepository;
  @Autowired private PropertyRepository propertyRepository;
  @Autowired private PropertyGroupRepository propertyGroupRepository;
  @Autowired private EntityTypeRepository entityTypeRepository;
  @Autowired private EntityRepository entityRepository;
  @Autowired private EntityPropertyRepository entityPropertyRepository;
  @Autowired private LayoutRepository layoutRepository;
  
  @PersistenceContext private EntityManager entityManager;

  @Override
  @Transactional
  @ExcludeFromJacocoGeneratedReport
  public void run(String... args) throws Exception {
    if (isJUnitTest()) {
      System.out.println("Skipping example entities initialization during JUnit tests");
      return;
    }

    var existingEntityType = entityTypeRepository.findByName("contact");
    if (existingEntityType.isPresent()) {
      System.out.println("Contact entity type already exists, skipping initialization");
      return;
    }

    System.out.println("Starting example entities initialization...");
    
    try {
      // Step 1: Create and save property types with proper persistence management
      createAndSavePropertyTypes();
      
      // Step 2: Create and save property group
      var contactPropertyGroup = createAndSavePropertyGroup();
      
      // Step 3: Create and save properties
      var properties = createAndSaveProperties(contactPropertyGroup);
      
      // Step 4: Create and save entity type
      var contactEntityType = createAndSaveEntityType(contactPropertyGroup);
      
      // Step 5: Create and save test entity with properties
      createAndSaveTestEntity(contactEntityType, properties);
      
      // Step 6: Create layouts with proper widget hierarchies
      createLayouts(contactPropertyGroup, properties);
      
      System.out.println("Example entities initialization completed successfully");
    } catch (Exception e) {
      System.err.println("Failed to initialize example entities: " + e.getMessage());
      e.printStackTrace();
      throw e;
    }
  }

  private void createAndSavePropertyTypes() {
    System.out.println("Creating property types...");
    
    // Create property types using setters to avoid builder issues
    PropertyType textPropertyType = new PropertyType();
    textPropertyType.setName("text");
    textPropertyType.setLabel("Text");
    textPropertyType.setSemanticType("Text");
    textPropertyType.setWireType("string");
    textPropertyType = propertyTypeRepository.save(textPropertyType);
    
    PropertyType numberPropertyType = new PropertyType();
    numberPropertyType.setName("number");
    numberPropertyType.setLabel("Number");
    numberPropertyType.setSemanticType("Number");
    numberPropertyType.setWireType("numeric");
    numberPropertyType.setNumeric(true);
    numberPropertyType = propertyTypeRepository.save(numberPropertyType);
    
    PropertyType datePropertyType = new PropertyType();
    datePropertyType.setName("date");
    datePropertyType.setLabel("Date");
    datePropertyType.setSemanticType("Date");
    datePropertyType.setWireType("timestamp");
    datePropertyType.setTimestamp(true);
    datePropertyType.setNumeric(true);
    datePropertyType = propertyTypeRepository.save(datePropertyType);
    
    PropertyType emailPropertyType = new PropertyType();
    emailPropertyType.setName("email");
    emailPropertyType.setLabel("Email");
    emailPropertyType.setSemanticType("email");
    emailPropertyType.setWireType("string");
    emailPropertyType = propertyTypeRepository.save(emailPropertyType);
    
    PropertyType phonePropertyType = new PropertyType();
    phonePropertyType.setName("phone");
    phonePropertyType.setLabel("Phone Number");
    phonePropertyType.setSemanticType("phone");
    phonePropertyType.setWireType("string");
    phonePropertyType = propertyTypeRepository.save(phonePropertyType);
    
    // Force flush to ensure all property types are persisted
    entityManager.flush();
    entityManager.clear(); // Clear session to avoid stale references
    
    System.out.println("Saved 5 property types");
  }

  private PropertyGroup createAndSavePropertyGroup() {
    System.out.println("Creating property group...");
    
    PropertyGroup contactPropertyGroup = new PropertyGroup();
    contactPropertyGroup.setLabel("General Information");
    contactPropertyGroup = propertyGroupRepository.save(contactPropertyGroup);
    
    entityManager.flush();
    System.out.println("Saved property group with ID: " + contactPropertyGroup.getId());
    
    return contactPropertyGroup;
  }

  private List<Property> createAndSaveProperties(PropertyGroup contactPropertyGroup) {
    System.out.println("Creating properties...");
    
    // Retrieve fresh property types from database to ensure proper attachment
    var allPropertyTypes = (List<PropertyType>) propertyTypeRepository.findAll();
    
    var textPropertyType = allPropertyTypes.stream()
        .filter(pt -> "text".equals(pt.getName()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Text property type not found"));
        
    var emailPropertyType = allPropertyTypes.stream()
        .filter(pt -> "email".equals(pt.getName()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Email property type not found"));
        
    var phonePropertyType = allPropertyTypes.stream()
        .filter(pt -> "phone".equals(pt.getName()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Phone property type not found"));

    // Create and save properties one by one using setters
    Property firstName = new Property();
    firstName.setName("first_name");
    firstName.setLabel("First Name");
    firstName.setDescription("Contact's first name");
    firstName.setPropertyType(textPropertyType);
    firstName.setPropertyGroupId(contactPropertyGroup.getId());
    firstName = propertyRepository.save(firstName);
    
    Property lastName = new Property();
    lastName.setName("last_name");
    lastName.setLabel("Last Name");
    lastName.setDescription("Contact's last name");
    lastName.setPropertyType(textPropertyType);
    lastName.setPropertyGroupId(contactPropertyGroup.getId());
    lastName = propertyRepository.save(lastName);
    
    Property email = new Property();
    email.setName("email");
    email.setLabel("Email");
    email.setDescription("Contact's email address");
    email.setPropertyType(emailPropertyType);
    email.setPropertyGroupId(contactPropertyGroup.getId());
    email = propertyRepository.save(email);
    
    Property phone = new Property();
    phone.setName("phone");
    phone.setLabel("Phone");
    phone.setDescription("Contact's phone number");
    phone.setPropertyType(phonePropertyType);
    phone.setPropertyGroupId(contactPropertyGroup.getId());
    phone = propertyRepository.save(phone);

    var properties = List.of(firstName, lastName, email, phone);
    entityManager.flush();
    System.out.println("Saved " + properties.size() + " properties individually");
    
    return properties;
  }

  private EntityType createAndSaveEntityType(PropertyGroup contactPropertyGroup) {
    System.out.println("Creating entity type...");
    
    EntityType contactEntityType = new EntityType();
    contactEntityType.setName("contact");
    contactEntityType.setLabel("Contact");
    contactEntityType.setPluralLabel("Contacts");
    contactEntityType.setEntityClass("contact");
    contactEntityType = entityTypeRepository.save(contactEntityType);
    
    // Update property group with entity type ID
    contactPropertyGroup.setEntityTypeId(contactEntityType.getId());
    propertyGroupRepository.save(contactPropertyGroup);
    
    entityManager.flush();
    System.out.println("Saved entity type with ID: " + contactEntityType.getId());
    
    return contactEntityType;
  }

  private void createAndSaveTestEntity(EntityType contactEntityType, List<Property> properties) {
    System.out.println("Creating test entity...");
    
    var orgId = UUID.fromString("123641f5-bdfd-4f27-915c-cc0b3c12a57a");
    Entity contactEntity = new Entity();
    contactEntity.setEntityType(contactEntityType);
    contactEntity.setOrganizationId(orgId);
    contactEntity = entityRepository.save(contactEntity);
    
    entityManager.flush();
    System.out.println("Saved entity with ID: " + contactEntity.getId());

    // Create entity properties individually
    var firstName = properties.stream()
        .filter(p -> "first_name".equals(p.getName()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("First name property not found"));
        
    var lastName = properties.stream()
        .filter(p -> "last_name".equals(p.getName()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Last name property not found"));
        
    var email = properties.stream()
        .filter(p -> "email".equals(p.getName()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Email property not found"));
        
    var phone = properties.stream()
        .filter(p -> "phone".equals(p.getName()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Phone property not found"));

    // Save each entity property individually to ensure proper persistence
    EntityProperty firstNameProperty = new EntityProperty();
    firstNameProperty.setEntity(contactEntity);
    firstNameProperty.setProperty(firstName);
    firstNameProperty.setValue("John");
    entityPropertyRepository.save(firstNameProperty);
    
    EntityProperty lastNameProperty = new EntityProperty();
    lastNameProperty.setEntity(contactEntity);
    lastNameProperty.setProperty(lastName);
    lastNameProperty.setValue("Doe");
    entityPropertyRepository.save(lastNameProperty);
    
    EntityProperty emailProperty = new EntityProperty();
    emailProperty.setEntity(contactEntity);
    emailProperty.setProperty(email);
    emailProperty.setValue("johndoe@studycrm.com");
    entityPropertyRepository.save(emailProperty);
    
    EntityProperty phoneProperty = new EntityProperty();
    phoneProperty.setEntity(contactEntity);
    phoneProperty.setProperty(phone);
    phoneProperty.setValue("123-456-7890");
    entityPropertyRepository.save(phoneProperty);
    
    entityManager.flush();
    System.out.println("Saved 4 entity properties individually");
  }

  private void createLayouts(PropertyGroup contactPropertyGroup, List<Property> properties) {
    System.out.println("Creating layouts...");
    
    createDetailsLayout(contactPropertyGroup, properties);
    createListLayout(contactPropertyGroup, properties);
    
    System.out.println("Layouts created successfully");
  }

  @ExcludeFromJacocoGeneratedReport
  private void createDetailsLayout(PropertyGroup contactPropertyGroup, List<Property> properties) {
    System.out.println("Creating details layout...");
    
    // Step 1: Create and save the main layout
    Layout layout = new Layout();
    layout.setEntityType("contact");
    layout.setTemplateName("contact_details");
    layout = layoutRepository.save(layout);
    
    entityManager.flush();
    System.out.println("Saved layout with ID: " + layout.getId());

    // Step 2: Create group widget
    GroupWidget group = new GroupWidget();
    group.setLabel("General Information");
    group.setPropertyGroupId(contactPropertyGroup.getId());

    // Step 3: Create property widgets for each property
    var displayOrder = new AtomicInteger();
    List<Widget> propertyWidgets = properties.stream()
        .map(property -> {
            PropertyWidget widget = new PropertyWidget();
            widget.setPropertyId(property.getId());
            widget.setLabel(property.getLabel());
            widget.setDisplayOrder(displayOrder.getAndIncrement());
            widget.setParentWidget(group);
            return (Widget) widget;
        })
        .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

    // Step 4: Set the widgets on the group
    group.setWidgets(propertyWidgets);

    // Step 5: Create the container with the group widget
    Container container = new Container();
    container.setLabel("General Information");
    container.setTemplateRegion("general_details");
    container.setContainerType(ContainerType.ACCORDION.name().toLowerCase());
    container.setLayout(layout);
    container.setWidgets(new ArrayList<>(List.of(group)));

    // Step 6: Set containers on layout and save (cascades will handle widgets)
    layout.setContainers(new ArrayList<>(List.of(container)));
    layoutRepository.save(layout);
    
    entityManager.flush();
    System.out.println("Details layout created successfully");
  }

  @ExcludeFromJacocoGeneratedReport
  private void createListLayout(PropertyGroup contactPropertyGroup, List<Property> properties) {
    System.out.println("Creating list layout for property group: " + contactPropertyGroup.getLabel());
    
    // Step 1: Create and save the main layout
    Layout layout = new Layout();
    layout.setEntityType("contact");
    layout.setTemplateName("contacts_list");
    layout = layoutRepository.save(layout);
    
    entityManager.flush();
    System.out.println("Saved list layout with ID: " + layout.getId());

    // Step 2: Create property widgets directly (no group widget for table layout)
    var displayOrder = new AtomicInteger();
    List<Widget> propertyWidgets = properties.stream()
        .map(property -> {
            PropertyWidget widget = new PropertyWidget();
            widget.setPropertyId(property.getId());
            widget.setLabel(property.getLabel());
            widget.setDisplayOrder(displayOrder.getAndIncrement());
            return (Widget) widget;
        })
        .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

    // Step 3: Create the container with property widgets
    Container container = new Container();
    container.setLabel("General Information");
    container.setTemplateRegion("contacts_table");
    container.setContainerType(ContainerType.TABLE.name().toLowerCase());
    container.setLayout(layout);
    container.setWidgets(propertyWidgets);

    // Step 4: Set containers on layout and save (cascades will handle widgets)
    layout.setContainers(new ArrayList<>(List.of(container)));
    layoutRepository.save(layout);
    
    entityManager.flush();
    System.out.println("List layout created successfully");
  }

  private static boolean isJUnitTest() {
    for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
      if (element.getClassName().startsWith("org.junit.")) {
        return true;
      }
    }
    return false;
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface ExcludeFromJacocoGeneratedReport {}
}