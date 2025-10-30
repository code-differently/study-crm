import { readFileSync } from 'fs';
import { join } from 'path';

export { resolvers } from './resolvers';
export { getClient } from './ssr-client';
export { ApolloWrapper } from './apollo-wrapper';

// Read the GraphQL schema file directly
export const typeDefs = readFileSync(
  join(process.cwd(), 'src/graphql/studycrm.graphql'),
  'utf8'
);
