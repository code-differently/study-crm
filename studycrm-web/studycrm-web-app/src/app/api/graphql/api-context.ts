import { EntitiesAPI } from '@/graphql/datasources/entities-api';
import { LayoutsAPI } from '@/graphql/datasources/layouts-api';
import { NextApiRequest } from 'next';

export interface ApiContext extends NextApiRequest {
  dataSources: {
    entitiesAPI: EntitiesAPI;
    layoutsAPI: LayoutsAPI;
  };
}
