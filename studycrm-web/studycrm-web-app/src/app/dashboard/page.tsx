import { auth, signIn } from '@/auth';
import { Card, Title, Text } from '@tremor/react';
import { gql } from '@/gql';
import { gqlClient } from '@/app/api/graphql/gql-client';
import Search from './search';
import UsersTable from './table';
import { redirect } from 'next/navigation';

interface User {
  id: string;
  name: string;
  username: string;
  email: string;
}

const entitiesQuery = gql(/* GraphQL */ `
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

export default async function IndexPage({
  searchParams
}: {
  searchParams: { q: string };
}) {
  const session = await auth();
  if (!session) {
    redirect('/auth/signin');
  }

  const result = await gqlClient.query({
    query: entitiesQuery, 
    variables: {type: 'contact'}
  });

  const users = new Array<User>();
  for (const entity of (result.data?.entities || [])) {
    const propertyMap = entity.properties.reduce((acc, property) => {
      acc[property.name] = property.value;
      return acc;
    }, {} as Record<string, string>);
    users.push({
      id: entity.id,
      name: propertyMap['first_name'] + ' ' + propertyMap['last_name'],
      username: propertyMap['email'],
      email: propertyMap['email'],
    });
  }

  return (
    <main className="p-4 md:p-10 mx-auto max-w-7xl">
      <Title>Users</Title>
      <Text>A list of users retrieved from a Postgres database.</Text>
      <Search />
      <Card className="mt-6">
        <UsersTable users={users} />
      </Card>
    </main>
  );
}