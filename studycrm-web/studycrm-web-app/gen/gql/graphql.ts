/* eslint-disable */
import { TypedDocumentNode as DocumentNode } from '@graphql-typed-document-node/core';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
export type MakeEmpty<T extends { [key: string]: unknown }, K extends keyof T> = { [_ in K]?: never };
export type Incremental<T> = T | { [P in keyof T]?: P extends ' $fragmentName' | '__typename' ? T[P] : never };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string; }
  String: { input: string; output: string; }
  Boolean: { input: boolean; output: boolean; }
  Int: { input: number; output: number; }
  Float: { input: number; output: number; }
};

export type AnyWidget = GroupWidget | PropertyWidget | Widget;

export type Container = {
  __typename?: 'Container';
  region?: Maybe<Scalars['String']['output']>;
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
  type: Scalars['String']['output'];
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
};

export type Property = {
  __typename?: 'Property';
  description: Scalars['String']['output'];
  groupId: Scalars['String']['output'];
  id: Scalars['ID']['output'];
  label: Scalars['String']['output'];
  name: Scalars['String']['output'];
  pluralLabel: Scalars['String']['output'];
  type: Scalars['String']['output'];
  typeId: Scalars['String']['output'];
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
  entities: Array<Entity>;
  layouts: Array<Layout>;
};


export type QueryEntitiesArgs = {
  type: Scalars['String']['input'];
};


export type QueryLayoutsArgs = {
  entityType: Scalars['String']['input'];
};

export type Widget = IWidget & {
  __typename?: 'Widget';
  displayOrder?: Maybe<Scalars['Int']['output']>;
  hideLabel?: Maybe<Scalars['Boolean']['output']>;
  label?: Maybe<Scalars['String']['output']>;
  type: Scalars['String']['output'];
};

export type QueryQueryVariables = Exact<{
  type: Scalars['String']['input'];
}>;


export type QueryQuery = { __typename?: 'Query', entities: Array<{ __typename?: 'Entity', id: string, type: string, properties: Array<{ __typename?: 'EntityProperty', name: string, type: string, value: string }> }>, layouts: Array<{ __typename?: 'Layout', id: string, entityType: string, containers: Array<{ __typename?: 'Container', region?: string | null, type?: string | null, widgets: Array<{ __typename?: 'GroupWidget', propertyGroupId: string, widgets: Array<{ __typename?: 'GroupWidget', type: string, label?: string | null, hideLabel?: boolean | null, displayOrder?: number | null, propertyGroupId: string, widgets: Array<{ __typename?: 'GroupWidget', type: string, label?: string | null, hideLabel?: boolean | null, displayOrder?: number | null, propertyGroupId: string } | { __typename?: 'PropertyWidget', type: string, label?: string | null, hideLabel?: boolean | null, displayOrder?: number | null, propertyId?: string | null } | { __typename?: 'Widget', type: string, label?: string | null, hideLabel?: boolean | null, displayOrder?: number | null }> } | { __typename?: 'PropertyWidget', type: string, label?: string | null, hideLabel?: boolean | null, displayOrder?: number | null, propertyId?: string | null } | { __typename?: 'Widget', type: string, label?: string | null, hideLabel?: boolean | null, displayOrder?: number | null }> } | { __typename?: 'PropertyWidget', propertyId?: string | null } | { __typename?: 'Widget', type: string, label?: string | null, hideLabel?: boolean | null, displayOrder?: number | null }> }> }> };


export const QueryDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"Query"},"variableDefinitions":[{"kind":"VariableDefinition","variable":{"kind":"Variable","name":{"kind":"Name","value":"type"}},"type":{"kind":"NonNullType","type":{"kind":"NamedType","name":{"kind":"Name","value":"String"}}}}],"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"entities"},"arguments":[{"kind":"Argument","name":{"kind":"Name","value":"type"},"value":{"kind":"Variable","name":{"kind":"Name","value":"type"}}}],"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"id"}},{"kind":"Field","name":{"kind":"Name","value":"type"}},{"kind":"Field","name":{"kind":"Name","value":"properties"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"name"}},{"kind":"Field","name":{"kind":"Name","value":"type"}},{"kind":"Field","name":{"kind":"Name","value":"value"}}]}}]}},{"kind":"Field","name":{"kind":"Name","value":"layouts"},"arguments":[{"kind":"Argument","name":{"kind":"Name","value":"entityType"},"value":{"kind":"Variable","name":{"kind":"Name","value":"type"}}}],"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"id"}},{"kind":"Field","name":{"kind":"Name","value":"entityType"}},{"kind":"Field","name":{"kind":"Name","value":"containers"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"region"}},{"kind":"Field","name":{"kind":"Name","value":"type"}},{"kind":"Field","name":{"kind":"Name","value":"widgets"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"InlineFragment","typeCondition":{"kind":"NamedType","name":{"kind":"Name","value":"Widget"}},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"type"}},{"kind":"Field","name":{"kind":"Name","value":"label"}},{"kind":"Field","name":{"kind":"Name","value":"hideLabel"}},{"kind":"Field","name":{"kind":"Name","value":"displayOrder"}}]}},{"kind":"InlineFragment","typeCondition":{"kind":"NamedType","name":{"kind":"Name","value":"PropertyWidget"}},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"propertyId"}}]}},{"kind":"InlineFragment","typeCondition":{"kind":"NamedType","name":{"kind":"Name","value":"GroupWidget"}},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"propertyGroupId"}},{"kind":"Field","name":{"kind":"Name","value":"widgets"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"InlineFragment","typeCondition":{"kind":"NamedType","name":{"kind":"Name","value":"Widget"}},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"type"}},{"kind":"Field","name":{"kind":"Name","value":"label"}},{"kind":"Field","name":{"kind":"Name","value":"hideLabel"}},{"kind":"Field","name":{"kind":"Name","value":"displayOrder"}}]}},{"kind":"InlineFragment","typeCondition":{"kind":"NamedType","name":{"kind":"Name","value":"PropertyWidget"}},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"type"}},{"kind":"Field","name":{"kind":"Name","value":"label"}},{"kind":"Field","name":{"kind":"Name","value":"hideLabel"}},{"kind":"Field","name":{"kind":"Name","value":"displayOrder"}},{"kind":"Field","name":{"kind":"Name","value":"propertyId"}}]}},{"kind":"InlineFragment","typeCondition":{"kind":"NamedType","name":{"kind":"Name","value":"GroupWidget"}},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"type"}},{"kind":"Field","name":{"kind":"Name","value":"label"}},{"kind":"Field","name":{"kind":"Name","value":"hideLabel"}},{"kind":"Field","name":{"kind":"Name","value":"displayOrder"}},{"kind":"Field","name":{"kind":"Name","value":"propertyGroupId"}},{"kind":"Field","name":{"kind":"Name","value":"widgets"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"InlineFragment","typeCondition":{"kind":"NamedType","name":{"kind":"Name","value":"Widget"}},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"type"}},{"kind":"Field","name":{"kind":"Name","value":"label"}},{"kind":"Field","name":{"kind":"Name","value":"hideLabel"}},{"kind":"Field","name":{"kind":"Name","value":"displayOrder"}}]}},{"kind":"InlineFragment","typeCondition":{"kind":"NamedType","name":{"kind":"Name","value":"PropertyWidget"}},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"type"}},{"kind":"Field","name":{"kind":"Name","value":"label"}},{"kind":"Field","name":{"kind":"Name","value":"hideLabel"}},{"kind":"Field","name":{"kind":"Name","value":"displayOrder"}},{"kind":"Field","name":{"kind":"Name","value":"propertyId"}}]}},{"kind":"InlineFragment","typeCondition":{"kind":"NamedType","name":{"kind":"Name","value":"GroupWidget"}},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"type"}},{"kind":"Field","name":{"kind":"Name","value":"label"}},{"kind":"Field","name":{"kind":"Name","value":"hideLabel"}},{"kind":"Field","name":{"kind":"Name","value":"displayOrder"}},{"kind":"Field","name":{"kind":"Name","value":"propertyGroupId"}}]}}]}}]}}]}}]}}]}}]}}]}}]}}]} as unknown as DocumentNode<QueryQuery, QueryQueryVariables>;