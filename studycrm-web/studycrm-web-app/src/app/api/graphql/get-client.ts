import { auth } from "@/auth";
import { EntitiesAPI } from './datasources/entities-api';
import { makeExecutableSchema } from '@graphql-tools/schema';
import { SchemaLink } from '@apollo/client/link/schema';
import resolvers from './resolvers';
import typeDefs from './studycrm.graphql';
import { ApolloClient, InMemoryCache } from "@apollo/client";

const schema = makeExecutableSchema({ typeDefs, resolvers });
const apolloCache = new InMemoryCache();

export const gqlClient = new ApolloClient({
  cache: apolloCache,
  link: new SchemaLink({ 
    schema, 
    context: async (req) => {
        const session = await auth();
        if (!session?.user?.organizationIds.length || !session?.accessToken) {
            throw new Error('Unauthorized');
        }
        const {user, accessToken} = session;
        return {
            req,
            dataSources: {
                entitiesAPI: new EntitiesAPI({user, accessToken}),
            },
        };
    }
  }),
});