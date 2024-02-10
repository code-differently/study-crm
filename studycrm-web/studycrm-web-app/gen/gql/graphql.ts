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

export type Query = {
  __typename?: 'Query';
  entities: Array<Entity>;
};


export type QueryEntitiesArgs = {
  type: Scalars['String']['input'];
};

export type QueryQueryVariables = Exact<{
  type: Scalars['String']['input'];
}>;


export type QueryQuery = { __typename?: 'Query', entities: Array<{ __typename?: 'Entity', id: string, type: string, properties: Array<{ __typename?: 'EntityProperty', name: string, type: string, value: string }> }> };


export const QueryDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"Query"},"variableDefinitions":[{"kind":"VariableDefinition","variable":{"kind":"Variable","name":{"kind":"Name","value":"type"}},"type":{"kind":"NonNullType","type":{"kind":"NamedType","name":{"kind":"Name","value":"String"}}}}],"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"entities"},"arguments":[{"kind":"Argument","name":{"kind":"Name","value":"type"},"value":{"kind":"Variable","name":{"kind":"Name","value":"type"}}}],"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"id"}},{"kind":"Field","name":{"kind":"Name","value":"type"}},{"kind":"Field","name":{"kind":"Name","value":"properties"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"name"}},{"kind":"Field","name":{"kind":"Name","value":"type"}},{"kind":"Field","name":{"kind":"Name","value":"value"}}]}}]}}]}}]} as unknown as DocumentNode<QueryQuery, QueryQueryVariables>;