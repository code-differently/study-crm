import { EntitiesAPI } from './datasources/entities-api';
import { LayoutsAPI } from './datasources/layouts-api';
import { NextApiRequest } from 'next';

export interface ApiContext extends NextApiRequest {
    dataSources: {
      entitiesAPI: EntitiesAPI;
      layoutsAPI: LayoutsAPI;
    };
}