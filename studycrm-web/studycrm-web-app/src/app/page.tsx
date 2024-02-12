import { cacheExchange, createClient, fetchExchange } from '@urql/core';
import { Card, Title, Text } from '@tremor/react';
import { cookies, headers } from 'next/headers'
import { gql } from '@/gql';
import { registerUrql } from '@urql/next/rsc';
import Search from './search';
import UsersTable from './table';
import { getClient } from './api/graphql/get-client';

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
  }
`);

export default async function IndexPage({
  searchParams
}: {
  searchParams: { q: string };
}) {
  const client = await getClient();
  const result = await client.query(entitiesQuery, { type: 'contact' });

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