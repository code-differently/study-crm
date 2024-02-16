import { auth } from '@/auth';
import { Card, Title, Text } from '@tremor/react';
import { ENTITIES_QUERY } from './entities-query';
import { getClient } from '@/graphql';
import { redirect } from 'next/navigation';
import Search from './search';
import UsersTable from './table';

interface User {
  id: string;
  name: string;
  username: string;
  email: string;
}

export default async function IndexPage({
  searchParams
}: {
  searchParams: { q: string };
}) {
  const session = await auth();
  if (!session) {
    redirect('/auth/signin');
  }

  const result = await getClient().query({
    query: ENTITIES_QUERY,
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