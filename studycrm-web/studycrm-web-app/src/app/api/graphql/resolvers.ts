const resolvers = {
    Query: {
        entities: (_, {type}, { dataSources }) => {
            return dataSources.entitiesAPI.getEntities(type);
        },
    },
};

export default resolvers;