import * as schema from './studycrm.graphql';
export { resolvers } from './resolvers';
export { getClient } from './ssr-client';
export { ApolloWrapper } from './apollo-wrapper';
export const typeDefs = schema;
