import { Card } from '@tremor/react';
import { ENTITIES_QUERY } from './entities-query';
import { getClient } from '@/graphql';
import Search from './search';
import UsersTable from './table';
import Widget from '@/app/components/widget';

interface User {
  id: string;
  name: string;
  username: string;
  email: string;
}

export default async function IndexPage() {
  const result = await getClient().query({
    query: ENTITIES_QUERY,
    variables: { type: 'contact' },
  });

  const users = new Array<User>();
  for (const entity of result.data?.entities || []) {
    const propertyMap = entity.properties.reduce(
      (acc, property) => {
        acc[property.name] = property.value;
        return acc;
      },
      {} as Record<string, string>
    );
    users.push({
      id: entity.id,
      name: propertyMap['first_name'] + ' ' + propertyMap['last_name'],
      username: propertyMap['email'],
      email: propertyMap['email'],
    });
  }

  return (
    <main className="p-4 md:p-10 mx-auto max-w-7xl">
      <h1>Users</h1>
      <p>A list of users retrieved from a Postgres database.</p>
      <Search />
      <Card className="mt-6">
        <Widget
          entities={result.data!.entities}
          spec={result.data!.layouts.layouts[0].containers[0]}
        ></Widget>
        <UsersTable users={users} />
      </Card>
    </main>
  );
}
