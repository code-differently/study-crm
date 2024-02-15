import { gql } from '@/gql';

export const ENTITIES_QUERY = gql(/* GraphQL */ `
  query Query($type: String!) {
    entities(type: $type) {
      id
      type
      properties {
        name
        type
        value
      }
    }
    layouts(entityType: $type) {
      id
      entityType
      containers {
        region
        type
        widgets {
          ... on Widget {
            type
            label
            hideLabel
            displayOrder
          }
          ... on PropertyWidget {
            propertyId
          }
          ... on GroupWidget {
            propertyGroupId
            widgets {
              ... on Widget {
                type
                label
                hideLabel
                displayOrder
              }
              ... on PropertyWidget {
                type
                label
                hideLabel
                displayOrder
                propertyId
              }
              ... on GroupWidget {
                type
                label
                hideLabel
                displayOrder
                propertyGroupId
                widgets {
                  ... on Widget {
                    type
                    label
                    hideLabel
                    displayOrder
                  }
                  ... on PropertyWidget {
                    type
                    label
                    hideLabel
                    displayOrder
                    propertyId
                  }
                  ... on GroupWidget {
                    type
                    label
                    hideLabel
                    displayOrder
                    propertyGroupId
                  }
                }
              }
            }
          }
        }
      }
    }
  }
`);