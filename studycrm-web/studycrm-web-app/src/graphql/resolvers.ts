import { Resolvers } from '@apollo/client';

export const resolvers: Resolvers = {
  Query: {
    entities: (_, { type }, { dataSources }) => {
      return dataSources.entitiesAPI.getEntities(type);
    },
    layouts: (_, { entityType, types }, { dataSources }) => {
      return dataSources.layoutsAPI.getLayouts(entityType, types);
    },
  },
  AnyWidget: {
    __resolveType(obj, contextValue, info) {
      if (obj.type === 'group') {
        return 'GroupWidget';
      }
      if (obj.type === 'property' || obj.type === 'field') {
        return 'PropertyWidget';
      }
      return 'Widget';
    },
  },
};
