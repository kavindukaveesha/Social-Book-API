# Social Book Network API - Project Documentation

## 1. Data Structures Implementation

### Title: Advanced Entity Modeling and Data Management
**Caption:** Comprehensive implementation of JPA entities with complex relationships, inheritance patterns, and optimized data structures for efficient book management, user authentication, and feedback systems.

#### Core Entities
- **Book Entity** (`src/main/java/com/NextCoreInv/book_network/book/Book.java`)
  - Advanced entity with builder pattern implementation
  - One-to-many relationships with feedback and transaction history
  - Custom specifications for dynamic querying

- **User Entity** (`src/main/java/com/NextCoreInv/book_network/user/User.java`)
  - Spring Security UserDetails implementation
  - Role-based access control with many-to-many relationships
  - Account lifecycle management (enabled, locked, expired states)

- **Feedback System** (`src/main/java/com/NextCoreInv/book_network/feedback/Feedback.java`)
  - Rating and comment management
  - User-book relationship tracking
  - Audit trail with BaseEntity inheritance

#### Advanced Data Structures
- **Custom Collections**: PageResponse for pagination (`src/main/java/com/NextCoreInv/book_network/common/PageResponse.java`)
- **Specification Pattern**: Dynamic query building (`src/main/java/com/NextCoreInv/book_network/book/BookSpecification.java`)
- **Builder Pattern**: Lombok-generated builders for complex object creation
- **Repository Pattern**: JPA repositories with custom query methods

---

## 2. Development Environment Setup

### Title: Production-Ready Spring Boot Development Stack
**Caption:** Comprehensive development environment configured with modern Java ecosystem tools, automated build processes, and developer productivity enhancements.

#### Technology Stack
- **Java 17** - Latest LTS version with modern language features
- **Spring Boot 3.2.2** - Enterprise-grade application framework
- **Maven 3.11.0** - Advanced build automation and dependency management
- **PostgreSQL** - Robust relational database system
- **Lombok** - Code generation for reduced boilerplate

#### Development Tools & Configuration
- **Spring Boot DevTools** - Hot reload and development utilities
- **Maven Compiler Plugin** - Annotation processing for Lombok integration
- **SpringDoc OpenAPI** - Automated API documentation generation
- **Profile-based Configuration** - Environment-specific settings (dev, prod)

#### Build Configuration (`pom.xml`)
```xml
<!-- Production-ready build setup with optimized packaging -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.2</version>
</parent>
```

---

## 3. RESTful Web Services Implementation

### Title: Comprehensive REST API with Advanced Features
**Caption:** Full-featured RESTful web services implementing CRUD operations, file uploads, pagination, authentication, and feedback management with OpenAPI documentation.

#### API Controllers

##### Book Management API (`src/main/java/com/NextCoreInv/book_network/book/BookController.java`)
- **POST /api/v1/books** - Create new books with validation
- **GET /api/v1/books/{id}** - Retrieve individual book details
- **GET /api/v1/books** - Paginated book listing with filtering
- **GET /api/v1/books/owner** - User's owned books management
- **GET /api/v1/books/borrowed** - Borrowed books tracking
- **PATCH /api/v1/books/shareable/{id}** - Update sharing permissions
- **POST /api/v1/books/cover/{id}** - Multipart file upload for book covers

##### Authentication API (`src/main/java/com/NextCoreInv/book_network/auth/AuthenticationController.java`)
- **POST /api/v1/auth/register** - User registration with email verification
- **POST /api/v1/auth/authentication** - JWT-based login system
- **GET /api/v1/auth/activate-account** - Email-based account activation

##### Feedback API (`src/main/java/com/NextCoreInv/book_network/feedback/FeedbackController.java`)
- **POST /api/v1/feedbacks** - Submit book reviews and ratings
- **GET /api/v1/feedbacks/book/{id}** - Paginated feedback retrieval

#### Advanced Features
- **JWT Authentication** - Stateless security implementation
- **File Upload Support** - Multipart form data handling (50MB limit)
- **Pagination & Sorting** - Efficient data retrieval with PageResponse
- **Input Validation** - Jakarta validation with custom error handling
- **OpenAPI Documentation** - Swagger UI integration

---

## 4. Database Connectivity Implementation

### Title: Enterprise Database Integration with JPA/Hibernate
**Caption:** Robust database connectivity featuring PostgreSQL integration, advanced JPA configurations, connection pooling, and comprehensive data persistence strategies.

#### Database Configuration (`application-dev.yml`)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bookDb
    username: postgres
    password: Kavindu12345
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        format_sql: true
    database: postgresql
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

#### Advanced Database Features

##### Repository Layer
- **BookRepository** - Custom query methods with JPQL
- **UserRepository** - Email-based user lookup and authentication
- **FeedbackRepository** - Complex joins for rating calculations
- **TokenRepository** - Secure token management for authentication

##### Data Persistence Strategies
- **Audit Trail** - BaseEntity with creation/modification tracking
- **Soft Deletes** - Logical deletion with archived status
- **Transaction Management** - ACID compliance with @Transactional
- **Connection Pooling** - Optimized database connection management

##### Database Schema Design
- **Normalized Relations** - Proper foreign key relationships
- **Indexing Strategy** - Performance-optimized database queries
- **Constraint Management** - Data integrity enforcement
- **Migration Support** - Hibernate DDL auto-update

---

## Additional Features

### Security Implementation
- **JWT Token Management** - Secure stateless authentication
- **Password Encryption** - BCrypt hashing algorithm
- **Role-Based Access Control** - Hierarchical permission system
- **CORS Configuration** - Cross-origin resource sharing setup

### Email Integration
- **SMTP Configuration** - Automated email notifications
- **Template Engine** - Thymeleaf-based email templates
- **Account Activation** - Email verification workflow

### File Management
- **File Storage Service** - Local file system integration
- **Image Processing** - Book cover upload and management
- **Security Validation** - File type and size restrictions

---

## Project Structure

```
src/
├── main/
│   ├── java/com/NextCoreInv/book_network/
│   │   ├── auth/          # Authentication & Authorization
│   │   ├── book/          # Book Management
│   │   ├── feedback/      # Review & Rating System
│   │   ├── user/          # User Management
│   │   ├── security/      # Security Configuration
│   │   ├── email/         # Email Services
│   │   └── common/        # Shared Components
│   └── resources/
│       ├── application.yml        # Main configuration
│       └── application-dev.yml    # Development settings
└── test/                  # Test Suite
```

## Getting Started

1. **Prerequisites**: Java 17, Maven 3.6+, PostgreSQL 12+
2. **Database Setup**: Create `bookDb` database
3. **Configuration**: Update `application-dev.yml` with your settings
4. **Build**: `mvn clean compile`
5. **Run**: `mvn spring-boot:run`
6. **API Documentation**: `http://localhost:8088/swagger-ui.html`

---
*Generated with Claude Code - Enterprise Spring Boot API Documentation*