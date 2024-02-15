/* eslint-disable */
import * as types from './graphql';
import { TypedDocumentNode as DocumentNode } from '@graphql-typed-document-node/core';

/**
 * Map of all GraphQL operations in the project.
 *
 * This map has several performance disadvantages:
 * 1. It is not tree-shakeable, so it will include all operations in the project.
 * 2. It is not minifiable, so the string of a GraphQL query will be multiple times inside the bundle.
 * 3. It does not support dead code elimination, so it will add unused operations.
 *
 * Therefore it is highly recommended to use the babel or swc plugin for production.
 */
const documents = {
    "\n  query Query($type: String!) {\n    entities(type: $type) {\n      id\n      type\n      properties {\n        name\n        typeName\n        value\n      }\n    }\n    layouts(entityType: $type) {\n      layouts {\n        id\n        entityType\n        containers {\n          region\n          type\n          widgets {\n            ... WidgetFields\n            ... PropertyWidgetFields\n            ... on GroupWidget {\n              ... GroupWidgetFields\n              widgets {\n                ... WidgetFields\n                ... PropertyWidgetFields\n                ... on GroupWidget {\n                  ... GroupWidgetFields\n                }\n              }\n            }\n          }\n        }\n      }\n      properties {\n        ... PropertyFields\n      }\n    }\n  }\n\n  fragment PropertyFields on Property {\n    id\n    name\n    label\n    pluralLabel\n    description\n    type {\n      ... PropertyTypeFields\n    }\n  }\n\n  fragment PropertyTypeFields on PropertyType {\n    name\n    label\n    semanticType\n    wireType\n    isNumeric\n    isTimestamp\n  }\n\n  fragment WidgetFields on Widget {\n    ... on Widget {\n      type\n      label\n      hideLabel\n      displayOrder\n    }\n  }\n\n  fragment PropertyWidgetFields on PropertyWidget {\n    ... on PropertyWidget {\n      type\n      label\n      hideLabel\n      displayOrder\n      propertyId\n    }\n  }\n\n  fragment GroupWidgetFields on GroupWidget {\n    type\n    label\n    hideLabel\n    displayOrder\n    propertyGroupId\n  }\n": types.QueryDocument,
};

/**
 * The gql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 *
 *
 * @example
 * ```ts
 * const query = gql(`query GetUser($id: ID!) { user(id: $id) { name } }`);
 * ```
 *
 * The query argument is unknown!
 * Please regenerate the types.
 */
export function gql(source: string): unknown;

/**
 * The gql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function gql(source: "\n  query Query($type: String!) {\n    entities(type: $type) {\n      id\n      type\n      properties {\n        name\n        typeName\n        value\n      }\n    }\n    layouts(entityType: $type) {\n      layouts {\n        id\n        entityType\n        containers {\n          region\n          type\n          widgets {\n            ... WidgetFields\n            ... PropertyWidgetFields\n            ... on GroupWidget {\n              ... GroupWidgetFields\n              widgets {\n                ... WidgetFields\n                ... PropertyWidgetFields\n                ... on GroupWidget {\n                  ... GroupWidgetFields\n                }\n              }\n            }\n          }\n        }\n      }\n      properties {\n        ... PropertyFields\n      }\n    }\n  }\n\n  fragment PropertyFields on Property {\n    id\n    name\n    label\n    pluralLabel\n    description\n    type {\n      ... PropertyTypeFields\n    }\n  }\n\n  fragment PropertyTypeFields on PropertyType {\n    name\n    label\n    semanticType\n    wireType\n    isNumeric\n    isTimestamp\n  }\n\n  fragment WidgetFields on Widget {\n    ... on Widget {\n      type\n      label\n      hideLabel\n      displayOrder\n    }\n  }\n\n  fragment PropertyWidgetFields on PropertyWidget {\n    ... on PropertyWidget {\n      type\n      label\n      hideLabel\n      displayOrder\n      propertyId\n    }\n  }\n\n  fragment GroupWidgetFields on GroupWidget {\n    type\n    label\n    hideLabel\n    displayOrder\n    propertyGroupId\n  }\n"): (typeof documents)["\n  query Query($type: String!) {\n    entities(type: $type) {\n      id\n      type\n      properties {\n        name\n        typeName\n        value\n      }\n    }\n    layouts(entityType: $type) {\n      layouts {\n        id\n        entityType\n        containers {\n          region\n          type\n          widgets {\n            ... WidgetFields\n            ... PropertyWidgetFields\n            ... on GroupWidget {\n              ... GroupWidgetFields\n              widgets {\n                ... WidgetFields\n                ... PropertyWidgetFields\n                ... on GroupWidget {\n                  ... GroupWidgetFields\n                }\n              }\n            }\n          }\n        }\n      }\n      properties {\n        ... PropertyFields\n      }\n    }\n  }\n\n  fragment PropertyFields on Property {\n    id\n    name\n    label\n    pluralLabel\n    description\n    type {\n      ... PropertyTypeFields\n    }\n  }\n\n  fragment PropertyTypeFields on PropertyType {\n    name\n    label\n    semanticType\n    wireType\n    isNumeric\n    isTimestamp\n  }\n\n  fragment WidgetFields on Widget {\n    ... on Widget {\n      type\n      label\n      hideLabel\n      displayOrder\n    }\n  }\n\n  fragment PropertyWidgetFields on PropertyWidget {\n    ... on PropertyWidget {\n      type\n      label\n      hideLabel\n      displayOrder\n      propertyId\n    }\n  }\n\n  fragment GroupWidgetFields on GroupWidget {\n    type\n    label\n    hideLabel\n    displayOrder\n    propertyGroupId\n  }\n"];

export function gql(source: string) {
  return (documents as any)[source] ?? {};
}

export type DocumentType<TDocumentNode extends DocumentNode<any, any>> = TDocumentNode extends DocumentNode<  infer TType,  any>  ? TType  : never;