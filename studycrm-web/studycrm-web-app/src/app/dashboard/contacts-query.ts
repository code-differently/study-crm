import { gql } from '@/gql';

export const CONTACTS_QUERY = gql(/* GraphQL */ `
  query GetContacts {
    contacts {
      id
      name
      username
      email
    }
  }
`);
