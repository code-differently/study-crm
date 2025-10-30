/* eslint-disable */
import { TypedDocumentNode as DocumentNode } from '@graphql-typed-document-node/core';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = {
  [K in keyof T]: T[K];
};
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & {
  [SubKey in K]?: Maybe<T[SubKey]>;
};
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & {
  [SubKey in K]: Maybe<T[SubKey]>;
};
export type MakeEmpty<
  T extends { [key: string]: unknown },
  K extends keyof T,
> = { [_ in K]?: never };
export type Incremental<T> =
  | T
  | {
      [P in keyof T]?: P extends ' $fragmentName' | '__typename' ? T[P] : never;
    };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string };
  String: { input: string; output: string };
  Boolean: { input: boolean; output: boolean };
  Int: { input: number; output: number };
  Float: { input: number; output: number };
};

export type AnyWidget = GroupWidget | PropertyWidget | Widget;

export type Contact = {
  __typename?: 'Contact';
  email: Scalars['String']['output'];
  id: Scalars['ID']['output'];
  name: Scalars['String']['output'];
  username: Scalars['String']['output'];
};

export type Container = {
  __typename?: 'Container';
  templateRegion: Scalars['String']['output'];
  type?: Maybe<Scalars['String']['output']>;
  widgets: Array<AnyWidget>;
};

export type Entity = {
  __typename?: 'Entity';
  id: Scalars['ID']['output'];
  properties: Array<EntityProperty>;
  type: Scalars['String']['output'];
};

export type EntityProperty = {
  __typename?: 'EntityProperty';
  name: Scalars['String']['output'];
  typeName: Scalars['String']['output'];
  value: Scalars['String']['output'];
};

export type GroupWidget = IWidget & {
  __typename?: 'GroupWidget';
  displayOrder?: Maybe<Scalars['Int']['output']>;
  hideLabel?: Maybe<Scalars['Boolean']['output']>;
  label?: Maybe<Scalars['String']['output']>;
  propertyGroupId: Scalars['String']['output'];
  type: Scalars['String']['output'];
  widgets: Array<AnyWidget>;
};

export type IWidget = {
  displayOrder?: Maybe<Scalars['Int']['output']>;
  hideLabel?: Maybe<Scalars['Boolean']['output']>;
  label?: Maybe<Scalars['String']['output']>;
  type: Scalars['String']['output'];
};

export type Layout = {
  __typename?: 'Layout';
  containers: Array<Container>;
  entityType: Scalars['String']['output'];
  id: Scalars['ID']['output'];
  templateName: Scalars['String']['output'];
};

export type LayoutResponse = {
  __typename?: 'LayoutResponse';
  layouts: Array<Layout>;
  properties: Array<Property>;
};

export type Property = {
  __typename?: 'Property';
  description?: Maybe<Scalars['String']['output']>;
  id: Scalars['ID']['output'];
  label?: Maybe<Scalars['String']['output']>;
  name?: Maybe<Scalars['String']['output']>;
  pluralLabel?: Maybe<Scalars['String']['output']>;
  type?: Maybe<PropertyType>;
};

export type PropertyType = {
  __typename?: 'PropertyType';
  isNumeric?: Maybe<Scalars['Boolean']['output']>;
  isTimestamp?: Maybe<Scalars['Boolean']['output']>;
  label?: Maybe<Scalars['String']['output']>;
  name?: Maybe<Scalars['String']['output']>;
  semanticType?: Maybe<Scalars['String']['output']>;
  wireType?: Maybe<Scalars['String']['output']>;
};

export type PropertyWidget = IWidget & {
  __typename?: 'PropertyWidget';
  displayOrder?: Maybe<Scalars['Int']['output']>;
  hideLabel?: Maybe<Scalars['Boolean']['output']>;
  label?: Maybe<Scalars['String']['output']>;
  propertyId?: Maybe<Scalars['String']['output']>;
  type: Scalars['String']['output'];
};

export type Query = {
  __typename?: 'Query';
  contacts: Array<Contact>;
  entities: Array<Entity>;
  layouts: LayoutResponse;
};

export type QueryEntitiesArgs = {
  type: Scalars['String']['input'];
};

export type QueryLayoutsArgs = {
  entityType: Scalars['String']['input'];
  types?: InputMaybe<Array<Scalars['String']['input']>>;
};

export type Widget = IWidget & {
  __typename?: 'Widget';
  displayOrder?: Maybe<Scalars['Int']['output']>;
  hideLabel?: Maybe<Scalars['Boolean']['output']>;
  label?: Maybe<Scalars['String']['output']>;
  type: Scalars['String']['output'];
};

export type GetContactsQueryVariables = Exact<{ [key: string]: never }>;

export type GetContactsQuery = {
  __typename?: 'Query';
  contacts: Array<{
    __typename?: 'Contact';
    id: string;
    name: string;
    username: string;
    email: string;
  }>;
};

export const GetContactsDocument = {
  kind: 'Document',
  definitions: [
    {
      kind: 'OperationDefinition',
      operation: 'query',
      name: { kind: 'Name', value: 'GetContacts' },
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          {
            kind: 'Field',
            name: { kind: 'Name', value: 'contacts' },
            selectionSet: {
              kind: 'SelectionSet',
              selections: [
                { kind: 'Field', name: { kind: 'Name', value: 'id' } },
                { kind: 'Field', name: { kind: 'Name', value: 'name' } },
                { kind: 'Field', name: { kind: 'Name', value: 'username' } },
                { kind: 'Field', name: { kind: 'Name', value: 'email' } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<GetContactsQuery, GetContactsQueryVariables>;
