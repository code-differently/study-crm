// Define resolver types based on the GraphQL schema
type Resolvers = {
  Query: {
    entities: (parent: any, args: { type: string }, context: any) => any;
    layouts: (
      parent: any,
      args: { entityType: string; types: any },
      context: any
    ) => any;
  };
  AnyWidget: {
    __resolveType: (obj: any, contextValue: any, info: any) => string;
  };
};

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
