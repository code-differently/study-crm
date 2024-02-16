import { gql } from '@/gql';

export const ENTITIES_QUERY = gql(/* GraphQL */ `
  query Query($type: String!) {
    entities(type: $type) {
      id
      type
      properties {
        name
        typeName
        value
      }
    }
    layouts(entityType: $type, types: null) {
      layouts {
        id
        templateName
        entityType
        containers {
          templateRegion
          widgets {
            ...WidgetFields
            ...PropertyWidgetFields
            ... on GroupWidget {
              ...GroupWidgetFields
              widgets {
                ...WidgetFields
                ...PropertyWidgetFields
                ... on GroupWidget {
                  ...GroupWidgetFields
                }
              }
            }
          }
        }
      }
      properties {
        ...PropertyFields
      }
    }
  }

  fragment PropertyFields on Property {
    id
    name
    label
    pluralLabel
    description
    type {
      ...PropertyTypeFields
    }
  }

  fragment PropertyTypeFields on PropertyType {
    name
    label
    semanticType
    wireType
    isNumeric
    isTimestamp
  }

  fragment WidgetFields on Widget {
    ... on Widget {
      type
      label
      hideLabel
      displayOrder
    }
  }

  fragment PropertyWidgetFields on PropertyWidget {
    ... on PropertyWidget {
      type
      label
      hideLabel
      displayOrder
      propertyId
    }
  }

  fragment GroupWidgetFields on GroupWidget {
    type
    label
    hideLabel
    displayOrder
    propertyGroupId
  }
`);
