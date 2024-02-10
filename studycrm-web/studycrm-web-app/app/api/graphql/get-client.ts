import { auth } from "@/auth";
import { cacheExchange, createClient, fetchExchange } from "@urql/next";
import { EntitiesAPI } from './datasources/entities-api';
import { executeExchange } from "@urql/exchange-execute";
import { makeExecutableSchema } from '@graphql-tools/schema';
import { registerUrql } from '@urql/next/rsc';
import resolvers from './resolvers';
import typeDefs from './studycrm.graphql';

const schema = makeExecutableSchema({ typeDefs, resolvers });
const makeClient = () => {
    return createClient({
        url: 'no-op',
        exchanges: [
            cacheExchange, 
            executeExchange({
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
            })
        ],
    });
};

export const { getClient } = registerUrql(makeClient);