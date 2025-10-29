# StudyCRM - High-Level Architecture

## Overview

StudyCRM is a microservices-based Customer Relationship Management system built using modern Java technologies and architectural patterns. The system is designed with event-driven architecture principles, leveraging the **Eventuate Tram framework** for distributed transactions and messaging coordination.

## System Components

### 1. Microservices Architecture

The system consists of **three core microservices** that handle different business domains:

#### **Auth Service** (`auth-service/`)
- **Purpose**: Authentication, authorization, and user management
- **Port**: 8081
- **Database**: MySQL (`auth-service-mysql`)
- **Key Responsibilities**:
  - User authentication via OAuth2/OIDC
  - JWT token management
  - User profile management
  - Role-based access control

#### **Entity Service** (`entity-service/`)
- **Purpose**: Dynamic entity and contact management
- **Port**: 8083  
- **Database**: MySQL (`entity-service-mysql`)
- **Key Responsibilities**:
  - Dynamic entity type definitions
  - Property and property group management
  - Layout configuration for UI rendering
  - Contact and entity CRUD operations

#### **Organization Service** (`organization-service/`)
- **Purpose**: Organization and tenant management
- **Port**: 8082
- **Database**: MySQL (`organization-service-mysql`)
- **Key Responsibilities**:
  - Organization lifecycle management
  - User-organization associations
  - Multi-tenant data isolation
  - **Saga orchestration** for cross-service operations

### 2. Frontend Application

#### **StudyCRM Web** (`studycrm-web/`)
- **Technology**: Next.js 14 with TypeScript
- **Architecture**: React-based Single Page Application
- **Key Features**:
  - GraphQL client with Apollo
  - NextAuth.js for authentication
  - Tremor UI components
  - Server-side rendering support

### 3. Infrastructure Components

#### **API Gateway** (Envoy Proxy)
- **Purpose**: Request routing and load balancing
- **Configuration**: `envoy.yaml`
- **Routes**:
  - Frontend: `local.studycrm.com` → StudyCRM Web
  - Auth endpoints: `/login`, `/oauth2` → Auth Service
  - API endpoints: `/api/*` → Respective services

#### **Event Streaming** (Kafka + Zookeeper)
- **Purpose**: Asynchronous messaging and event streaming
- **Used by**: Eventuate Tram for saga coordination

#### **Change Data Capture** (CDC Service)
- **Purpose**: Captures database changes for event publishing
- **Integration**: Eventuate CDC for distributed transaction patterns

#### **Observability** (Zipkin)
- **Purpose**: Distributed tracing and monitoring
- **Integration**: Spring Cloud Sleuth for trace collection

## Architectural Patterns

### 1. **Modular Monolith Pattern (Per Service)**

Each microservice follows a layered modular architecture:

```
service-name/
├── service-main/           # Main application entry point
├── service-domain/         # Core business logic
├── service-persistence/    # Data access layer
├── service-web/           # REST API controllers
├── service-messaging/     # Event handlers
├── service-api-web/       # Web API contracts
├── service-api-messaging/ # Messaging contracts
└── service-sagas/         # Saga orchestration (Organization only)
```

**Key Benefits**:
- Clear separation of concerns
- Independent deployability per layer
- Testability through dependency injection
- Gradle subproject organization

### 2. **Saga Pattern for Distributed Transactions**

The system implements the **Saga orchestration pattern** using Eventuate Tram:

#### **CreateOrganizationSaga** Example:
```java
@Service
public class CreateOrganizationSaga implements SimpleSaga<CreateOrganizationSagaData> {
    
    private SagaDefinition<CreateOrganizationSagaData> sagaDefinition =
        step()
            .invokeLocal(this::saveOrganizationAndUser)     // Step 1: Local transaction
            .step()
            .invokeParticipant(this::createAuthUser)        // Step 2: Remote service call
            .onReply(AuthUserNotCreated.class, this::handleAuthUserNotCreated) // Compensation
            .step()
            .invokeLocal(this::handleAuthUserCreated)       // Step 3: Completion
            .build();
}
```

**Benefits**:
- Ensures data consistency across services
- Automatic compensation for failed operations
- Reliable distributed transaction coordination

### 3. **Command Query Responsibility Segregation (CQRS)**

Services separate command operations (writes) from query operations (reads):

- **Commands**: Handled via messaging with saga coordination
- **Queries**: Direct database access through repositories
- **Events**: Published for cross-service communication

### 4. **Domain-Driven Design (DDD)**

Each service is organized around a specific business domain:

- **Bounded Contexts**: Auth, Entities, Organizations
- **Aggregates**: Organization, Entity, User
- **Domain Services**: Business logic encapsulation
- **Repositories**: Data access abstraction

### 5. **Build Logic Conventions**

The project uses **Gradle Convention Plugins** for consistent builds:

#### **Plugin Types**:
- `java-shared`: Common Java dependencies and configurations
- `spring-library`: Spring Boot library modules
- `java-web-application`: Main application entry points
- `node-application`: Frontend applications

#### **Dependency Management**:
- **Platforms**: Centralized version management (`platforms/`)
- **Build Logic**: Reusable build configurations (`build-logic/`)
- **Multi-module**: Gradle composite builds

## Security Architecture

### 1. **OAuth2/OIDC Authentication**
- Integration with external identity providers
- JWT token-based authentication
- Centralized user management in Auth Service

### 2. **Method-Level Security**
- Spring Security `@PreAuthorize` annotations
- Role-based access control (RBAC)
- Organization-scoped permissions

### 3. **Inter-Service Communication**
- JWT token propagation between services
- Resource server configuration for API protection

## Data Architecture

### 1. **Database Per Service**
- Each microservice owns its data
- MySQL databases with Flyway migrations
- Event sourcing through Eventuate tables

### 2. **Dynamic Schema (Entity Service)**
- Runtime entity type definitions
- Property-based flexible data modeling
- Layout-driven UI generation

### 3. **Multi-tenancy (Organization Service)**
- Organization-scoped data isolation
- User-organization access control
- Tenant context propagation

## Development Patterns

### 1. **Configuration Management**
- Environment-specific profiles (development, production)
- Docker Compose orchestration
- Gradle task automation

### 2. **Testing Strategies**
- Unit tests with Mockito
- Integration tests with TestContainers
- Saga testing with Eventuate test support

### 3. **Code Quality**
- Lombok for boilerplate reduction
- JaCoCo for coverage reporting
- ESLint/Prettier for frontend code quality

## Deployment Architecture

### 1. **Containerization**
- Docker images for each service
- Health check endpoints (`/actuator/health`)
- Lightweight base images (Amazon Corretto)

### 2. **Service Discovery**
- Docker Compose networking
- Environment-based service URLs
- Load balancing through Envoy

### 3. **Development Workflow**
- VS Code Dev Containers support
- Gradle composite build system
- Hot reloading for development

## Technology Stack Summary

| Component | Technology | Purpose |
|-----------|------------|---------|
| **Backend Services** | Java 17, Spring Boot 3 | Microservice development |
| **Messaging** | Eventuate Tram, Kafka | Event-driven architecture |
| **Databases** | MySQL 8 | Persistent data storage |
| **Frontend** | Next.js 14, TypeScript | User interface |
| **API Gateway** | Envoy Proxy | Request routing |
| **Build System** | Gradle 8 | Multi-module builds |
| **Containerization** | Docker, Docker Compose | Development & deployment |
| **Observability** | Zipkin, Spring Actuator | Monitoring & tracing |

## Getting Started for New Developers

1. **Prerequisites**: Install VS Code, Docker, Java 17, and Node.js
2. **Setup**: Open project in VS Code Dev Container
3. **Start Services**: Run `gradle start` to launch all components
4. **Access Points**:
   - Frontend: `http://local.studycrm.com`
   - API: `http://api-local.studycrm.com`
   - Zipkin: `http://localhost:9411`

5. **Key Entry Points**:
   - Service main classes: `*/service-main/*/App.java`
   - Frontend: `studycrm-web/studycrm-web-app/src/app/page.tsx`
   - API documentation: Service endpoints at `/v3/api-docs`

This architecture enables scalable, maintainable, and resilient CRM functionality while following modern microservices best practices.