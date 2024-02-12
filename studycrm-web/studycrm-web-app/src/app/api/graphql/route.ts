import { ApolloServer } from '@apollo/server';
import { auth } from '@/auth';
import { EntitiesAPI } from './datasources/entities-api';
import { NextApiRequest } from 'next';
import { startServerAndCreateNextHandler } from '@as-integrations/next';
import resolvers from './resolvers';
import typeDefs from './studycrm.graphql';


const server = new ApolloServer({
  resolvers,
  typeDefs,
});

interface ApiContext extends NextApiRequest {
  dataSources: {
    entitiesAPI: EntitiesAPI;
  };
}

const handler = startServerAndCreateNextHandler<ApiContext>(server, {
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
    },
  });

export { handler as GET, handler as POST };
