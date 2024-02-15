import { Resolvers } from "@apollo/client";

const resolvers: Resolvers = {
    Query: {
        entities: (_, {type}, { dataSources }) => {
            return dataSources.entitiesAPI.getEntities(type);
        },
        layouts: (_, {entityType}, { dataSources }) => {
            return dataSources.layoutsAPI.getLayouts(entityType);
        },
    },
    AnyWidget: {
      __resolveType(obj, contextValue, info){
        if(obj.type === 'group'){
          return 'GroupWidget';
        }
        if(obj.type === 'property' || obj.type === 'field'){
          return 'PropertyWidget';
        }
        return 'Widget';
      },
    },
};

export default resolvers;