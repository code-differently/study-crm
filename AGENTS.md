# StudyCRM - AI Coding Assistant Instructions

## Architecture Overview

StudyCRM is a **microservices-based CRM** using **event-driven architecture** with Eventuate Tram for distributed transactions. The system consists of three core services: **Auth Service** (port 8081), **Entity Service** (port 8083), and **Organization Service** (port 8082), plus a **Next.js frontend** with GraphQL federation.

## Essential Workflows

### Development Commands
- **Start all services**: `./gradlew  start` (includes Docker containers, databases, Kafka)
- **Start minimal infrastructure**: `./gradlew  startBarebones` (databases, Kafka, CDC only)
- **Run tests**: `./gradlew  test` (across all services)
- **Generate coverage**: `./gradlew  coverage` (JaCoCo reports)
- **Code formatting**: `./gradlew  fix` (Spotless + Prettier)
- **Teardown**: `./gradlew  teardown` (stop all containers)

### Frontend Commands (in `studycrm-web/studycrm-web-app/`)
- **Development**: `npm run dev` (Turbopack enabled)
- **GraphQL codegen**: `npm run generate` (runs automatically on dev/build)

## Service Architecture Pattern

Each microservice follows a **modular monolith structure**:
```
service-name/
├── service-main/           # Application entry point (@SpringBootApplication)
├── service-domain/         # Business logic & entities  
├── service-persistence/    # JPA repositories & database access
├── service-web/           # REST controllers & GraphQL resolvers
├── service-messaging/     # Eventuate event handlers & saga participants
├── service-api-web/       # API contracts (requests/responses)
├── service-api-messaging/ # Event/command definitions
└── service-sagas/         # Saga orchestration (Organization Service only)
```

## Key Patterns & Conventions

### Saga Pattern Implementation
- **Organization Service** orchestrates cross-service transactions using Eventuate Tram
- Example: `CreateOrganizationSaga` coordinates user creation across Auth and Organization services
- Saga participants implement `CommandHandler` interfaces
- Commands are sent via `send(command).to("serviceDestination").build()`

### Authentication Flow
- **Backend**: Spring Security with OAuth2/OIDC using custom `AuthServerSecurityConfig`
- **Frontend**: NextAuth.js with custom OIDC provider (`auth.ts`)
- **Token Management**: JWT access tokens with refresh token rotation
- **Middleware**: Custom proxy (`src/proxy.ts`) handles session validation and redirects

### GraphQL Federation
- **Server-side**: Apollo Server at `/api/graphql` with custom datasources
- **Client-side**: Apollo Client with SSR support via `ApolloWrapper`
- **Codegen**: GraphQL Code Generator creates typed queries from `src/graphql/studycrm.graphql`
- **Datasources**: REST-to-GraphQL adapters in `src/graphql/datasources/`

### Build System (Gradle)
- **Convention Plugins**: `java-shared`, `spring-library`, `java-web-application`, `node-application`
- **Platforms**: Centralized dependency versions in `platforms/java-platform/`
- **Docker Compose**: Configured service groups (barebones, infrastructure, services)
- **Coverage**: JaCoCo aggregation across all Java modules

## Important Integration Points

### Database & Messaging
- **Eventuate CDC**: Captures MySQL changes for event publishing
- **Kafka**: Event streaming backbone (port 29092 internally)
- **Databases**: Separate MySQL instances per service with Flyway migrations

### Service Communication
- **Synchronous**: REST APIs via Envoy proxy gateway
- **Asynchronous**: Kafka events via Eventuate Tram framework
- **Frontend-Backend**: GraphQL over HTTP with JWT authentication

### Development Environment
- **Hosts Configuration**: Requires `local.studycrm.com` and `api-local.studycrm.com` → `127.0.0.1`
- **Dev Containers**: Full VS Code devcontainer support
- **Hot Reload**: Frontend via Turbopack, Backend via Spring Boot DevTools

## Common File Locations
- **Service Main Classes**: `*/service-main/*/src/main/java/*/App.java`
- **GraphQL Schema**: `studycrm-web/studycrm-web-app/src/graphql/studycrm.graphql`
- **Docker Compose**: `docker-compose.yaml` (services defined with health checks)
- **Envoy Config**: `envoy.yaml` (API gateway routing rules)
- **Frontend Pages**: `studycrm-web/studycrm-web-app/src/app/` (App Router structure)

## Testing Conventions
- **Unit Tests**: JUnit 5 with Mockito in each service module
- **Integration Tests**: Separate test suites with TestContainers
- **Coverage**: Exclude methods annotated with `@ExcludeFromJacocoGeneratedReport`
- **Frontend**: Jest/React Testing Library (configured but minimal coverage)

When modifying services, always consider the saga choreography and ensure proper event publishing for cross-service coordination.