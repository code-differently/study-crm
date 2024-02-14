package com.codedifferently.studycrm.entities.web.config;

import com.codedifferently.studycrm.entities.domain.*;
import com.codedifferently.studycrm.entities.layout.domain.*;
import jakarta.transaction.Transactional;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExampleEntitiesInitializer implements CommandLineRunner {

  @Autowired private PropertyTypeRepository propertyTypeRepository;

  @Autowired private PropertyRepository propertyRepository;

  @Autowired private PropertyGroupRepository propertyGroupRepository;

  @Autowired private EntityTypeRepository entityTypeRepository;

  @Autowired private EntityRepository entityRepository;

  @Autowired private EntityPropertyRepository entityPropertyRepository;

  @Autowired private LayoutRepository layoutRepository;

  @Override
  @Transactional
  @ExcludeFromJacocoGeneratedReport
  public void run(String... args) throws Exception {
    if (isJUnitTest()) {
      return;
    }

    var entityType = entityTypeRepository.findByName("contact");
    if (entityType.isPresent()) {
      return;
    }

    // Create property types
    var textPropertyType =
        PropertyType.builder()
            .name("text")
            .label("Text")
            .semanticType("Text")
            .wireType("string")
            .build();
    var numberPropertyType =
        PropertyType.builder()
            .name("number")
            .label("Number")
            .semanticType("Number")
            .wireType("numeric")
            .isNumeric(true)
            .build();
    var datePropertyType =
        PropertyType.builder()
            .name("date")
            .label("Date")
            .semanticType("Date")
            .wireType("timestamp")
            .isTimestamp(true)
            .isNumeric(true)
            .build();
    var emailPropertyType =
        PropertyType.builder()
            .name("email")
            .label("Email")
            .semanticType("email")
            .wireType("string")
            .build();
    var phonePropertyType =
        PropertyType.builder()
            .name("phone")
            .label("Phone Number")
            .semanticType("phone")
            .wireType("string")
            .build();

    var propertyTypes =
        List.of(
            textPropertyType,
            numberPropertyType,
            datePropertyType,
            emailPropertyType,
            phonePropertyType);
    Objects.requireNonNull(propertyTypes);
    propertyTypeRepository.saveAll(propertyTypes);

    // Create properties
    var firstName =
        Property.builder()
            .name("first_name")
            .label("First Name")
            .description("Contact's first name")
            .propertyType(textPropertyType)
            .build();
    var lastName =
        Property.builder()
            .name("last_name")
            .label("Last Name")
            .description("Contact's last name")
            .propertyType(textPropertyType)
            .build();
    var email =
        Property.builder()
            .name("email")
            .label("Email")
            .description("Contact's email address")
            .propertyType(emailPropertyType)
            .build();
    var phone =
        Property.builder()
            .name("phone")
            .label("Phone")
            .description("Contact's phone number")
            .propertyType(phonePropertyType)
            .build();
    var properties = List.of(firstName, lastName, email, phone);
    Objects.requireNonNull(properties);
    propertyRepository.saveAll(properties);

    // Create property groups
    var contactPropertyGroup =
        PropertyGroup.builder()
            .label("General Information")
            .properties(List.of(firstName, lastName, email, phone))
            .build();
    Objects.requireNonNull(contactPropertyGroup);
    propertyGroupRepository.save(contactPropertyGroup);

    // Create entity types
    var contactEntityType =
        EntityType.builder()
            .name("contact")
            .label("Contact")
            .pluralLabel("Contacts")
            .propertyGroups(List.of(contactPropertyGroup))
            .entityClass("contact")
            .build();

    Objects.requireNonNull(contactEntityType);
    entityTypeRepository.save(contactEntityType);

    // Create test entity
    var orgId = UUID.fromString("123641f5-bdfd-4f27-915c-cc0b3c12a57a");
    var contactEntity =
        Entity.builder().entityType(contactEntityType).organizationId(orgId).build();

    Objects.requireNonNull(contactEntity);
    entityRepository.save(contactEntity);

    // Create entity properties
    var contactProperties =
        List.of(
            EntityProperty.builder()
                .entity(contactEntity)
                .property(firstName)
                .value("John")
                .build(),
            EntityProperty.builder().entity(contactEntity).property(lastName).value("Doe").build(),
            EntityProperty.builder()
                .entity(contactEntity)
                .property(email)
                .value("johndoe@studycrm.com")
                .build(),
            EntityProperty.builder()
                .entity(contactEntity)
                .property(phone)
                .value("123-456-7890")
                .build());
    Objects.requireNonNull(contactProperties);
    entityPropertyRepository.saveAll(contactProperties);

    // Create layouts
    createLayout(contactPropertyGroup);
  }

  @ExcludeFromJacocoGeneratedReport
  private void createLayout(PropertyGroup contactPropertyGroup) {
    var layout = Layout.builder().entityType("contact").build();
    Objects.requireNonNull(layout);
    layoutRepository.save(layout);

    var group =
        GroupWidget.builder()
            .label("General Information")
            .propertyGroupId(contactPropertyGroup.getId())
            .build();

    List<Widget> widgets =
        contactPropertyGroup.getProperties().stream()
            .map(
                property ->
                    (Widget)
                        FieldWidget.builder()
                            .parentWidget(group)
                            .propertyId(property.getId())
                            .label(property.getLabel())
                            .build())
            .toList();

    group.setWidgets(widgets);

    var container =
        Container.builder()
            .label("General Information")
            .region("contact")
            .containerType(ContainerType.ACCORDION)
            .layout(layout)
            .widgets(Arrays.asList(group))
            .build();

    layout.setContainers(Arrays.asList(container));

    layoutRepository.save(layout);
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
