# Example GraphQL response

The following is an example response returned from the GraphQL server in the studycrm-web fe app. There are two major parts to this:

1. The `entities` which are instances of entities of a given type.
2. The `layouts` collection which presents metadata on how entity information is to be displayed.

```gql
{
  "data": {
    "entities": [
      {
        "__typename": "Entity",
        "id": "4f280932-13d5-4349-97a7-4c8c8ce3b1b3",
        "type": "contact",
        "properties": [
          {
            "__typename": "EntityProperty",
            "name": "last_name",
            "typeName": "text",
            "value": "Doe"
          },
          {
            "__typename": "EntityProperty",
            "name": "phone",
            "typeName": "phone",
            "value": "123-456-7890"
          },
          {
            "__typename": "EntityProperty",
            "name": "first_name",
            "typeName": "text",
            "value": "John"
          },
          {
            "__typename": "EntityProperty",
            "name": "email",
            "typeName": "email",
            "value": "johndoe@studycrm.com"
          }
        ]
      }
    ],
    "layouts": {
      "__typename": "LayoutResponse",
      "layouts": [
        {
          "__typename": "Layout",
          "id": "4675ba9c-d745-4dbb-9111-53ceb041f1b5",
          "templateName": "contact_details",
          "entityType": "contact",
          "containers": [
            {
              "__typename": "Container",
              "templateRegion": "general_details",
              "type": "accordion",
              "widgets": [
                {
                  "__typename": "GroupWidget",
                  "widgets": [
                    {
                      "__typename": "PropertyWidget",
                      "type": "property",
                      "label": "Last Name",
                      "hideLabel": false,
                      "displayOrder": 1,
                      "propertyId": "28c8d693-d093-4963-86bf-8c0af500dc0b"
                    },
                    {
                      "__typename": "PropertyWidget",
                      "type": "property",
                      "label": "Email",
                      "hideLabel": false,
                      "displayOrder": 2,
                      "propertyId": "83d027a8-843f-45e4-b336-40193fadc574"
                    },
                    {
                      "__typename": "PropertyWidget",
                      "type": "property",
                      "label": "First Name",
                      "hideLabel": false,
                      "displayOrder": 0,
                      "propertyId": "dfe16533-a4e0-460a-bd0c-9d03a59d6552"
                    },
                    {
                      "__typename": "PropertyWidget",
                      "type": "property",
                      "label": "Phone",
                      "hideLabel": false,
                      "displayOrder": 3,
                      "propertyId": "78d911fa-7783-45a2-87b3-29e6c6ef06f0"
                    }
                  ],
                  "type": "group",
                  "label": "General Information",
                  "hideLabel": false,
                  "displayOrder": 0,
                  "propertyGroupId": "8860f652-817e-4dd0-80e0-65fd213d370d"
                }
              ]
            }
          ]
        },
        {
          "__typename": "Layout",
          "id": "765457b6-965a-482e-94e3-9d379bd06469",
          "templateName": "contacts_list",
          "entityType": "contact",
          "containers": [
            {
              "__typename": "Container",
              "templateRegion": "contacts_table",
              "type": "table",
              "widgets": [
                {
                  "__typename": "PropertyWidget",
                  "type": "property",
                  "label": "First Name",
                  "hideLabel": false,
                  "displayOrder": 0,
                  "propertyId": "dfe16533-a4e0-460a-bd0c-9d03a59d6552"
                },
                {
                  "__typename": "PropertyWidget",
                  "type": "property",
                  "label": "Email",
                  "hideLabel": false,
                  "displayOrder": 2,
                  "propertyId": "83d027a8-843f-45e4-b336-40193fadc574"
                },
                {
                  "__typename": "PropertyWidget",
                  "type": "property",
                  "label": "Last Name",
                  "hideLabel": false,
                  "displayOrder": 1,
                  "propertyId": "28c8d693-d093-4963-86bf-8c0af500dc0b"
                },
                {
                  "__typename": "PropertyWidget",
                  "type": "property",
                  "label": "Phone",
                  "hideLabel": false,
                  "displayOrder": 3,
                  "propertyId": "78d911fa-7783-45a2-87b3-29e6c6ef06f0"
                }
              ]
            }
          ]
        }
      ],
      "properties": [
        {
          "__typename": "Property",
          "id": "28c8d693-d093-4963-86bf-8c0af500dc0b",
          "name": "last_name",
          "label": "Last Name",
          "pluralLabel": null,
          "description": "Contact's last name",
          "type": {
            "__typename": "PropertyType",
            "name": "text",
            "label": "Text",
            "semanticType": "Text",
            "wireType": "string",
            "isNumeric": null,
            "isTimestamp": null
          }
        },
        {
          "__typename": "Property",
          "id": "78d911fa-7783-45a2-87b3-29e6c6ef06f0",
          "name": "phone",
          "label": "Phone",
          "pluralLabel": null,
          "description": "Contact's phone number",
          "type": {
            "__typename": "PropertyType",
            "name": "phone",
            "label": "Phone Number",
            "semanticType": "phone",
            "wireType": "string",
            "isNumeric": null,
            "isTimestamp": null
          }
        },
        {
          "__typename": "Property",
          "id": "83d027a8-843f-45e4-b336-40193fadc574",
          "name": "email",
          "label": "Email",
          "pluralLabel": null,
          "description": "Contact's email address",
          "type": {
            "__typename": "PropertyType",
            "name": "email",
            "label": "Email",
            "semanticType": "email",
            "wireType": "string",
            "isNumeric": null,
            "isTimestamp": null
          }
        },
        {
          "__typename": "Property",
          "id": "dfe16533-a4e0-460a-bd0c-9d03a59d6552",
          "name": "first_name",
          "label": "First Name",
          "pluralLabel": null,
          "description": "Contact's first name",
          "type": {
            "__typename": "PropertyType",
            "name": "text",
            "label": "Text",
            "semanticType": "Text",
            "wireType": "string",
            "isNumeric": null,
            "isTimestamp": null
          }
        }
      ]
    }
  }
}
```