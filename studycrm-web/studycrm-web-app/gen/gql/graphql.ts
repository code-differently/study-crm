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

export type QueryQueryVariables = Exact<{
  type: Scalars['String']['input'];
}>;

export type QueryQuery = {
  __typename?: 'Query';
  entities: Array<{
    __typename?: 'Entity';
    id: string;
    type: string;
    properties: Array<{
      __typename?: 'EntityProperty';
      name: string;
      typeName: string;
      value: string;
    }>;
  }>;
  layouts: {
    __typename?: 'LayoutResponse';
    layouts: Array<{
      __typename?: 'Layout';
      id: string;
      templateName: string;
      entityType: string;
      containers: Array<{
        __typename?: 'Container';
        templateRegion: string;
        type?: string | null;
        widgets: Array<
          | ({
              __typename?: 'GroupWidget';
              widgets: Array<
                | ({ __typename?: 'GroupWidget' } & {
                    ' $fragmentRefs'?: {
                      GroupWidgetFieldsFragment: GroupWidgetFieldsFragment;
                    };
                  })
                | ({ __typename?: 'PropertyWidget' } & {
                    ' $fragmentRefs'?: {
                      PropertyWidgetFieldsFragment: PropertyWidgetFieldsFragment;
                    };
                  })
                | ({ __typename?: 'Widget' } & {
                    ' $fragmentRefs'?: {
                      WidgetFieldsFragment: WidgetFieldsFragment;
                    };
                  })
              >;
            } & {
              ' $fragmentRefs'?: {
                GroupWidgetFieldsFragment: GroupWidgetFieldsFragment;
              };
            })
          | ({ __typename?: 'PropertyWidget' } & {
              ' $fragmentRefs'?: {
                PropertyWidgetFieldsFragment: PropertyWidgetFieldsFragment;
              };
            })
          | ({ __typename?: 'Widget' } & {
              ' $fragmentRefs'?: { WidgetFieldsFragment: WidgetFieldsFragment };
            })
        >;
      }>;
    }>;
    properties: Array<
      { __typename?: 'Property' } & {
        ' $fragmentRefs'?: { PropertyFieldsFragment: PropertyFieldsFragment };
      }
    >;
  };
};

export type PropertyFieldsFragment = {
  __typename?: 'Property';
  id: string;
  name?: string | null;
  label?: string | null;
  pluralLabel?: string | null;
  description?: string | null;
  type?:
    | ({ __typename?: 'PropertyType' } & {
        ' $fragmentRefs'?: {
          PropertyTypeFieldsFragment: PropertyTypeFieldsFragment;
        };
      })
    | null;
} & { ' $fragmentName'?: 'PropertyFieldsFragment' };

export type PropertyTypeFieldsFragment = {
  __typename?: 'PropertyType';
  name?: string | null;
  label?: string | null;
  semanticType?: string | null;
  wireType?: string | null;
  isNumeric?: boolean | null;
  isTimestamp?: boolean | null;
} & { ' $fragmentName'?: 'PropertyTypeFieldsFragment' };

export type WidgetFieldsFragment = {
  __typename?: 'Widget';
  type: string;
  label?: string | null;
  hideLabel?: boolean | null;
  displayOrder?: number | null;
} & { ' $fragmentName'?: 'WidgetFieldsFragment' };

export type PropertyWidgetFieldsFragment = {
  __typename?: 'PropertyWidget';
  type: string;
  label?: string | null;
  hideLabel?: boolean | null;
  displayOrder?: number | null;
  propertyId?: string | null;
} & { ' $fragmentName'?: 'PropertyWidgetFieldsFragment' };

export type GroupWidgetFieldsFragment = {
  __typename?: 'GroupWidget';
  type: string;
  label?: string | null;
  hideLabel?: boolean | null;
  displayOrder?: number | null;
  propertyGroupId: string;
} & { ' $fragmentName'?: 'GroupWidgetFieldsFragment' };

export const PropertyTypeFieldsFragmentDoc = {
  kind: 'Document',
  definitions: [
    {
      kind: 'FragmentDefinition',
      name: { kind: 'Name', value: 'PropertyTypeFields' },
      typeCondition: {
        kind: 'NamedType',
        name: { kind: 'Name', value: 'PropertyType' },
      },
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          { kind: 'Field', name: { kind: 'Name', value: 'name' } },
          { kind: 'Field', name: { kind: 'Name', value: 'label' } },
          { kind: 'Field', name: { kind: 'Name', value: 'semanticType' } },
          { kind: 'Field', name: { kind: 'Name', value: 'wireType' } },
          { kind: 'Field', name: { kind: 'Name', value: 'isNumeric' } },
          { kind: 'Field', name: { kind: 'Name', value: 'isTimestamp' } },
        ],
      },
    },
  ],
} as unknown as DocumentNode<PropertyTypeFieldsFragment, unknown>;
export const PropertyFieldsFragmentDoc = {
  kind: 'Document',
  definitions: [
    {
      kind: 'FragmentDefinition',
      name: { kind: 'Name', value: 'PropertyFields' },
      typeCondition: {
        kind: 'NamedType',
        name: { kind: 'Name', value: 'Property' },
      },
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          { kind: 'Field', name: { kind: 'Name', value: 'id' } },
          { kind: 'Field', name: { kind: 'Name', value: 'name' } },
          { kind: 'Field', name: { kind: 'Name', value: 'label' } },
          { kind: 'Field', name: { kind: 'Name', value: 'pluralLabel' } },
          { kind: 'Field', name: { kind: 'Name', value: 'description' } },
          {
            kind: 'Field',
            name: { kind: 'Name', value: 'type' },
            selectionSet: {
              kind: 'SelectionSet',
              selections: [
                {
                  kind: 'FragmentSpread',
                  name: { kind: 'Name', value: 'PropertyTypeFields' },
                },
              ],
            },
          },
        ],
      },
    },
    {
      kind: 'FragmentDefinition',
      name: { kind: 'Name', value: 'PropertyTypeFields' },
      typeCondition: {
        kind: 'NamedType',
        name: { kind: 'Name', value: 'PropertyType' },
      },
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          { kind: 'Field', name: { kind: 'Name', value: 'name' } },
          { kind: 'Field', name: { kind: 'Name', value: 'label' } },
          { kind: 'Field', name: { kind: 'Name', value: 'semanticType' } },
          { kind: 'Field', name: { kind: 'Name', value: 'wireType' } },
          { kind: 'Field', name: { kind: 'Name', value: 'isNumeric' } },
          { kind: 'Field', name: { kind: 'Name', value: 'isTimestamp' } },
        ],
      },
    },
  ],
} as unknown as DocumentNode<PropertyFieldsFragment, unknown>;
export const WidgetFieldsFragmentDoc = {
  kind: 'Document',
  definitions: [
    {
      kind: 'FragmentDefinition',
      name: { kind: 'Name', value: 'WidgetFields' },
      typeCondition: {
        kind: 'NamedType',
        name: { kind: 'Name', value: 'Widget' },
      },
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          {
            kind: 'InlineFragment',
            typeCondition: {
              kind: 'NamedType',
              name: { kind: 'Name', value: 'Widget' },
            },
            selectionSet: {
              kind: 'SelectionSet',
              selections: [
                { kind: 'Field', name: { kind: 'Name', value: 'type' } },
                { kind: 'Field', name: { kind: 'Name', value: 'label' } },
                { kind: 'Field', name: { kind: 'Name', value: 'hideLabel' } },
                {
                  kind: 'Field',
                  name: { kind: 'Name', value: 'displayOrder' },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<WidgetFieldsFragment, unknown>;
export const PropertyWidgetFieldsFragmentDoc = {
  kind: 'Document',
  definitions: [
    {
      kind: 'FragmentDefinition',
      name: { kind: 'Name', value: 'PropertyWidgetFields' },
      typeCondition: {
        kind: 'NamedType',
        name: { kind: 'Name', value: 'PropertyWidget' },
      },
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          {
            kind: 'InlineFragment',
            typeCondition: {
              kind: 'NamedType',
              name: { kind: 'Name', value: 'PropertyWidget' },
            },
            selectionSet: {
              kind: 'SelectionSet',
              selections: [
                { kind: 'Field', name: { kind: 'Name', value: 'type' } },
                { kind: 'Field', name: { kind: 'Name', value: 'label' } },
                { kind: 'Field', name: { kind: 'Name', value: 'hideLabel' } },
                {
                  kind: 'Field',
                  name: { kind: 'Name', value: 'displayOrder' },
                },
                { kind: 'Field', name: { kind: 'Name', value: 'propertyId' } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<PropertyWidgetFieldsFragment, unknown>;
export const GroupWidgetFieldsFragmentDoc = {
  kind: 'Document',
  definitions: [
    {
      kind: 'FragmentDefinition',
      name: { kind: 'Name', value: 'GroupWidgetFields' },
      typeCondition: {
        kind: 'NamedType',
        name: { kind: 'Name', value: 'GroupWidget' },
      },
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          { kind: 'Field', name: { kind: 'Name', value: 'type' } },
          { kind: 'Field', name: { kind: 'Name', value: 'label' } },
          { kind: 'Field', name: { kind: 'Name', value: 'hideLabel' } },
          { kind: 'Field', name: { kind: 'Name', value: 'displayOrder' } },
          { kind: 'Field', name: { kind: 'Name', value: 'propertyGroupId' } },
        ],
      },
    },
  ],
} as unknown as DocumentNode<GroupWidgetFieldsFragment, unknown>;
export const QueryDocument = {
  kind: 'Document',
  definitions: [
    {
      kind: 'OperationDefinition',
      operation: 'query',
      name: { kind: 'Name', value: 'Query' },
      variableDefinitions: [
        {
          kind: 'VariableDefinition',
          variable: { kind: 'Variable', name: { kind: 'Name', value: 'type' } },
          type: {
            kind: 'NonNullType',
            type: {
              kind: 'NamedType',
              name: { kind: 'Name', value: 'String' },
            },
          },
        },
      ],
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          {
            kind: 'Field',
            name: { kind: 'Name', value: 'entities' },
            arguments: [
              {
                kind: 'Argument',
                name: { kind: 'Name', value: 'type' },
                value: {
                  kind: 'Variable',
                  name: { kind: 'Name', value: 'type' },
                },
              },
            ],
            selectionSet: {
              kind: 'SelectionSet',
              selections: [
                { kind: 'Field', name: { kind: 'Name', value: 'id' } },
                { kind: 'Field', name: { kind: 'Name', value: 'type' } },
                {
                  kind: 'Field',
                  name: { kind: 'Name', value: 'properties' },
                  selectionSet: {
                    kind: 'SelectionSet',
                    selections: [
                      { kind: 'Field', name: { kind: 'Name', value: 'name' } },
                      {
                        kind: 'Field',
                        name: { kind: 'Name', value: 'typeName' },
                      },
                      { kind: 'Field', name: { kind: 'Name', value: 'value' } },
                    ],
                  },
                },
              ],
            },
          },
          {
            kind: 'Field',
            name: { kind: 'Name', value: 'layouts' },
            arguments: [
              {
                kind: 'Argument',
                name: { kind: 'Name', value: 'entityType' },
                value: {
                  kind: 'Variable',
                  name: { kind: 'Name', value: 'type' },
                },
              },
              {
                kind: 'Argument',
                name: { kind: 'Name', value: 'types' },
                value: { kind: 'NullValue' },
              },
            ],
            selectionSet: {
              kind: 'SelectionSet',
              selections: [
                {
                  kind: 'Field',
                  name: { kind: 'Name', value: 'layouts' },
                  selectionSet: {
                    kind: 'SelectionSet',
                    selections: [
                      { kind: 'Field', name: { kind: 'Name', value: 'id' } },
                      {
                        kind: 'Field',
                        name: { kind: 'Name', value: 'templateName' },
                      },
                      {
                        kind: 'Field',
                        name: { kind: 'Name', value: 'entityType' },
                      },
                      {
                        kind: 'Field',
                        name: { kind: 'Name', value: 'containers' },
                        selectionSet: {
                          kind: 'SelectionSet',
                          selections: [
                            {
                              kind: 'Field',
                              name: { kind: 'Name', value: 'templateRegion' },
                            },
                            {
                              kind: 'Field',
                              name: { kind: 'Name', value: 'type' },
                            },
                            {
                              kind: 'Field',
                              name: { kind: 'Name', value: 'widgets' },
                              selectionSet: {
                                kind: 'SelectionSet',
                                selections: [
                                  {
                                    kind: 'FragmentSpread',
                                    name: {
                                      kind: 'Name',
                                      value: 'WidgetFields',
                                    },
                                  },
                                  {
                                    kind: 'FragmentSpread',
                                    name: {
                                      kind: 'Name',
                                      value: 'PropertyWidgetFields',
                                    },
                                  },
                                  {
                                    kind: 'InlineFragment',
                                    typeCondition: {
                                      kind: 'NamedType',
                                      name: {
                                        kind: 'Name',
                                        value: 'GroupWidget',
                                      },
                                    },
                                    selectionSet: {
                                      kind: 'SelectionSet',
                                      selections: [
                                        {
                                          kind: 'FragmentSpread',
                                          name: {
                                            kind: 'Name',
                                            value: 'GroupWidgetFields',
                                          },
                                        },
                                        {
                                          kind: 'Field',
                                          name: {
                                            kind: 'Name',
                                            value: 'widgets',
                                          },
                                          selectionSet: {
                                            kind: 'SelectionSet',
                                            selections: [
                                              {
                                                kind: 'FragmentSpread',
                                                name: {
                                                  kind: 'Name',
                                                  value: 'WidgetFields',
                                                },
                                              },
                                              {
                                                kind: 'FragmentSpread',
                                                name: {
                                                  kind: 'Name',
                                                  value: 'PropertyWidgetFields',
                                                },
                                              },
                                              {
                                                kind: 'InlineFragment',
                                                typeCondition: {
                                                  kind: 'NamedType',
                                                  name: {
                                                    kind: 'Name',
                                                    value: 'GroupWidget',
                                                  },
                                                },
                                                selectionSet: {
                                                  kind: 'SelectionSet',
                                                  selections: [
                                                    {
                                                      kind: 'FragmentSpread',
                                                      name: {
                                                        kind: 'Name',
                                                        value:
                                                          'GroupWidgetFields',
                                                      },
                                                    },
                                                  ],
                                                },
                                              },
                                            ],
                                          },
                                        },
                                      ],
                                    },
                                  },
                                ],
                              },
                            },
                          ],
                        },
                      },
                    ],
                  },
                },
                {
                  kind: 'Field',
                  name: { kind: 'Name', value: 'properties' },
                  selectionSet: {
                    kind: 'SelectionSet',
                    selections: [
                      {
                        kind: 'FragmentSpread',
                        name: { kind: 'Name', value: 'PropertyFields' },
                      },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
    {
      kind: 'FragmentDefinition',
      name: { kind: 'Name', value: 'PropertyTypeFields' },
      typeCondition: {
        kind: 'NamedType',
        name: { kind: 'Name', value: 'PropertyType' },
      },
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          { kind: 'Field', name: { kind: 'Name', value: 'name' } },
          { kind: 'Field', name: { kind: 'Name', value: 'label' } },
          { kind: 'Field', name: { kind: 'Name', value: 'semanticType' } },
          { kind: 'Field', name: { kind: 'Name', value: 'wireType' } },
          { kind: 'Field', name: { kind: 'Name', value: 'isNumeric' } },
          { kind: 'Field', name: { kind: 'Name', value: 'isTimestamp' } },
        ],
      },
    },
    {
      kind: 'FragmentDefinition',
      name: { kind: 'Name', value: 'WidgetFields' },
      typeCondition: {
        kind: 'NamedType',
        name: { kind: 'Name', value: 'Widget' },
      },
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          {
            kind: 'InlineFragment',
            typeCondition: {
              kind: 'NamedType',
              name: { kind: 'Name', value: 'Widget' },
            },
            selectionSet: {
              kind: 'SelectionSet',
              selections: [
                { kind: 'Field', name: { kind: 'Name', value: 'type' } },
                { kind: 'Field', name: { kind: 'Name', value: 'label' } },
                { kind: 'Field', name: { kind: 'Name', value: 'hideLabel' } },
                {
                  kind: 'Field',
                  name: { kind: 'Name', value: 'displayOrder' },
                },
              ],
            },
          },
        ],
      },
    },
    {
      kind: 'FragmentDefinition',
      name: { kind: 'Name', value: 'PropertyWidgetFields' },
      typeCondition: {
        kind: 'NamedType',
        name: { kind: 'Name', value: 'PropertyWidget' },
      },
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          {
            kind: 'InlineFragment',
            typeCondition: {
              kind: 'NamedType',
              name: { kind: 'Name', value: 'PropertyWidget' },
            },
            selectionSet: {
              kind: 'SelectionSet',
              selections: [
                { kind: 'Field', name: { kind: 'Name', value: 'type' } },
                { kind: 'Field', name: { kind: 'Name', value: 'label' } },
                { kind: 'Field', name: { kind: 'Name', value: 'hideLabel' } },
                {
                  kind: 'Field',
                  name: { kind: 'Name', value: 'displayOrder' },
                },
                { kind: 'Field', name: { kind: 'Name', value: 'propertyId' } },
              ],
            },
          },
        ],
      },
    },
    {
      kind: 'FragmentDefinition',
      name: { kind: 'Name', value: 'GroupWidgetFields' },
      typeCondition: {
        kind: 'NamedType',
        name: { kind: 'Name', value: 'GroupWidget' },
      },
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          { kind: 'Field', name: { kind: 'Name', value: 'type' } },
          { kind: 'Field', name: { kind: 'Name', value: 'label' } },
          { kind: 'Field', name: { kind: 'Name', value: 'hideLabel' } },
          { kind: 'Field', name: { kind: 'Name', value: 'displayOrder' } },
          { kind: 'Field', name: { kind: 'Name', value: 'propertyGroupId' } },
        ],
      },
    },
    {
      kind: 'FragmentDefinition',
      name: { kind: 'Name', value: 'PropertyFields' },
      typeCondition: {
        kind: 'NamedType',
        name: { kind: 'Name', value: 'Property' },
      },
      selectionSet: {
        kind: 'SelectionSet',
        selections: [
          { kind: 'Field', name: { kind: 'Name', value: 'id' } },
          { kind: 'Field', name: { kind: 'Name', value: 'name' } },
          { kind: 'Field', name: { kind: 'Name', value: 'label' } },
          { kind: 'Field', name: { kind: 'Name', value: 'pluralLabel' } },
          { kind: 'Field', name: { kind: 'Name', value: 'description' } },
          {
            kind: 'Field',
            name: { kind: 'Name', value: 'type' },
            selectionSet: {
              kind: 'SelectionSet',
              selections: [
                {
                  kind: 'FragmentSpread',
                  name: { kind: 'Name', value: 'PropertyTypeFields' },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<QueryQuery, QueryQueryVariables>;
