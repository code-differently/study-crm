import { Card } from '@tremor/react';
import { CONTACTS_QUERY } from './contacts-query';
import { getClient } from '@/graphql';
import Search from './search';
import ContactsTable from './table';
import type { GetContactsQuery } from '@/gql/graphql';

export default async function IndexPage() {
  const result = await getClient().query<GetContactsQuery>({
    query: CONTACTS_QUERY,
  });

  const contacts = result.data?.contacts || [];

  return (
    <main className="p-4 md:p-10 mx-auto max-w-7xl">
      <h1>Users</h1>
      <p>A list of users retrieved from a Postgres database.</p>
      <Search />
      <Card className="mt-6">
        <ContactsTable contacts={contacts} />
      </Card>
    </main>
  );
}
