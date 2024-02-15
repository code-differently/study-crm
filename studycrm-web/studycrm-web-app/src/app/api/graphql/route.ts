import { ApolloServer } from '@apollo/server';
import { auth } from '@/auth';
import { EntitiesAPI } from './datasources/entities-api';
import { startServerAndCreateNextHandler } from '@as-integrations/next';
import resolvers from './resolvers';
import typeDefs from './studycrm.graphql';
import { ApiContext } from './api-context';
import { LayoutsAPI } from './datasources/layouts-api';


const server = new ApolloServer({
  resolvers,
  typeDefs,
});


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
          layoutsAPI: new LayoutsAPI({user, accessToken}),
        },
      };
    },
  });

  
export { handler as GET, handler as POST };
