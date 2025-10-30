import { ApolloClient, InMemoryCache } from '@apollo/client';
import { auth } from '@/auth';
import { cookies } from 'next/headers';
import { EntitiesAPI } from './datasources/entities-api';
import { LayoutsAPI } from './datasources/layouts-api';
import { makeExecutableSchema } from '@graphql-tools/schema';
import { registerApolloClient } from '@apollo/client-integration-nextjs';
import { SchemaLink } from '@apollo/client/link/schema';
import { resolvers } from './resolvers';
import { readFileSync } from 'fs';
import { join } from 'path';

// Read the GraphQL schema file directly
const typeDefs = readFileSync(
  join(process.cwd(), 'src/graphql/studycrm.graphql'),
  'utf8'
);

const schema = makeExecutableSchema({ typeDefs, resolvers });
const apolloCache = new InMemoryCache();

export const { getClient } = registerApolloClient(
  () =>
    new ApolloClient({
      cache: apolloCache,
      link: new SchemaLink({
        schema,
        context: async (req) => {
          const session = await auth();

          // Check for session errors (like token refresh failures)
          if (session?.error === 'RefreshAccessTokenError') {
            throw new Error('Authentication expired. Please sign in again.');
          }

          if (!session?.user?.organizationIds.length || !session?.accessToken) {
            throw new Error('Unauthorized');
          }

          const { user, accessToken } = session;
          return {
            req,
            dataSources: {
              entitiesAPI: new EntitiesAPI({ user, accessToken }),
              layoutsAPI: new LayoutsAPI({ user, accessToken }),
            },
          };
        },
      }),
    })
);
