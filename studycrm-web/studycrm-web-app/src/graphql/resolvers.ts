// Define resolver types based on the GraphQL schema
type Resolvers = {
  Query: {
    entities: (parent: any, args: { type: string }, context: any) => any;
    contacts: (parent: any, args: {}, context: any) => any;
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
    contacts: async (_, {}, { dataSources }) => {
      const entities = await dataSources.entitiesAPI.getEntities('contact');

      return entities.map((entity: any) => {
        const propertyMap = entity.properties.reduce(
          (acc: Record<string, string>, property: any) => {
            acc[property.name] = property.value;
            return acc;
          },
          {} as Record<string, string>
        );

        return {
          id: entity.id,
          name: `${propertyMap['first_name'] || ''} ${propertyMap['last_name'] || ''}`.trim(),
          username: propertyMap['email'] || '',
          email: propertyMap['email'] || '',
        };
      });
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
