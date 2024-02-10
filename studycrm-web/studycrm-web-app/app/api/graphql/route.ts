import { startServerAndCreateNextHandler } from '@as-integrations/next';
import { ApolloServer } from '@apollo/server';
import { EntitiesAPI } from './datasources/entities-api';
import resolvers from './resolvers';
import typeDefs from './studycrm.graphql';
import { getToken } from "next-auth/jwt"
import { NextApiRequest } from 'next';


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
      const token = await getToken({ req, secret: process.env.NEXTAUTH_SECRET });
      if (!token?.user?.organizationIds.length) {
        throw new Error('Unauthorized');
      }
      const {user, accessToken} = token;
      return { 
        req,
        dataSources: {
          entitiesAPI: new EntitiesAPI({user, accessToken}),
        },
      };
    },
  });

export { handler as GET, handler as POST };
