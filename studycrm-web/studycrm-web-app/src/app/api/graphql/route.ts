import { ApiContext } from './api-context';
import { ApolloServer } from '@apollo/server';
import { auth } from '@/auth';
import { EntitiesAPI, LayoutsAPI } from '@/graphql/datasources';
import { startServerAndCreateNextHandler } from '@as-integrations/next';
import { resolvers, typeDefs } from '@/graphql';

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
    const { user, accessToken } = session;
    return {
      req,
      dataSources: {
        entitiesAPI: new EntitiesAPI({ user, accessToken }),
        layoutsAPI: new LayoutsAPI({ user, accessToken }),
      },
    };
  },
});

export { handler as GET, handler as POST };
