# Weflow Server

Collaborative Project Management System for Web Agencies

## Overview

Weflow is a backend server for project collaboration between web agencies and clients. It provides features for tracking project progress, step-by-step approval requests, checklists, notifications, file management, and more.

### Key Features

- **User Management**: Role-based access control for system administrators, agencies, and clients
- **Project Management**: Project creation, member management, and step-by-step progress tracking
- **Steps & Approval Requests**: Approval workflow management for each project phase
- **Posts & Comments**: Project-specific boards with Q&A functionality
- **Checklists**: Checklist templates and project-specific checklist management
- **Notifications**: Email, SMS, and in-app notification support
- **File Management**: File upload and management using AWS S3
- **Dashboard**: Statistics dashboards for administrators, users, and projects
- **Activity Logs**: Audit logs for all major operations

## Tech Stack

### Core Framework
- **Java**: 21 (Virtual Threads support)
- **Spring Boot**: 3.5.7
- **Spring Security**: JWT-based authentication/authorization
- **Spring Data JPA**: Data access layer

### Database
- **MySQL**: 8.x (Production)
- **H2**: In-Memory (Testing)
- **QueryDSL**: 5.0.0 (Dynamic queries)

### Authentication & Security
- **JWT**: io.jsonwebtoken:jjwt (0.11.5)
- **BCrypt**: Password encryption
- **Spring Security**: RBAC-based access control

### External Services Integration
- **AWS S3**: File storage (SDK v2)
- **Solapi**: SMS service
- **Gmail SMTP**: Email delivery

### API Documentation
- **Springdoc OpenAPI**: 3.0 (Swagger UI)

### Logging & Monitoring
- **Elasticsearch**: 7.17.9 (Log storage and search)
- **Kibana**: 7.17.9 (Log visualization and analysis)
- **Filebeat**: 7.17.9 (Log collection and forwarding)
- **Logstash Logback**: JSON logging encoder

### Build & Deployment
- **Gradle**: 8.14.3
- **Docker**: Containerization
- **Docker Compose**: Local development environment

### Other Libraries
- **Lombok**: Code simplification
- **Apache Commons CSV**: CSV file processing

## System Requirements

- Java 21 or higher
- MySQL 8.x or higher
- Gradle 8.x or higher

## Getting Started

### Environment Setup

1. Clone the repository
```bash
git clone https://github.com/your-org/weflow-server.git
cd weflow-server
```

2. Build
```bash
./gradlew build
```

3. Run
```bash
./gradlew bootRun
```

Or using Docker Compose:
```bash
docker-compose up
```

### API Documentation

After running the server, you can access the API documentation at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Project Structure

```
weflow-server
├── src/main/java/com/rdc/weflow_server
│   ├── controller/          # REST API controllers
│   │   ├── auth/           # Authentication
│   │   ├── user/           # User management
│   │   ├── project/        # Project management
│   │   ├── step/           # Steps and approval requests
│   │   ├── post/           # Posts
│   │   ├── comment/        # Comments
│   │   ├── checklist/      # Checklists
│   │   ├── notification/   # Notifications
│   │   └── ...
│   ├── service/            # Business logic
│   ├── repository/         # Data access
│   ├── entity/             # JPA entities
│   ├── dto/                # Data transfer objects
│   ├── config/             # Configuration classes
│   ├── common/             # Common utilities
│   └── exception/          # Exception handling
├── src/main/resources
│   ├── application.yml     # Base configuration
│   ├── application-local.yml
│   ├── application-prod.yml
│   └── application-test.yml
├── docs/                   # Documentation
└── build.gradle            # Gradle build configuration
```

## Key API Endpoints

### Authentication
- `POST /api/auth/login` - Login (JWT token issuance)

### Users
- `GET /api/users/me` - Get my profile
- `PUT /api/users/me` - Update my profile
- `POST /api/admin/users` - Create user (Admin)
- `GET /api/admin/users` - Get user list (Admin)

### Projects
- `GET /api/projects` - Get my projects
- `GET /api/projects/{id}` - Get project details
- `POST /api/admin/projects` - Create project (Admin)
- `PUT /api/admin/projects/{id}` - Update project (Admin)

### Steps & Approval Requests
- `GET /api/projects/{projectId}/steps` - Get project steps
- `POST /api/steps/{stepId}/requests` - Create approval request
- `GET /api/requests` - Get my approval requests
- `POST /api/requests/{id}/approve` - Approve request
- `POST /api/requests/{id}/reject` - Reject request

### Posts
- `GET /api/projects/{projectId}/posts` - Get posts
- `POST /api/projects/{projectId}/posts` - Create post
- `GET /api/projects/{projectId}/posts/{id}` - Get post details
- `PUT /api/projects/{projectId}/posts/{id}` - Update post
- `DELETE /api/projects/{projectId}/posts/{id}` - Delete post

### Notifications
- `GET /api/notifications` - Get notifications
- `GET /api/notifications/unread/count` - Get unread notification count
- `PUT /api/notifications/{id}/read` - Mark notification as read

### Files
- `GET /api/files/presigned-url` - Generate S3 Presigned URL

## Security

- JWT-based stateless authentication
- BCrypt password encryption
- Role-based access control (RBAC)
  - `SYSTEM_ADMIN`: System-wide administration
  - `AGENCY`: Agency staff
  - `CLIENT`: Client staff
  - `GUEST`: Guest access
- CORS configuration (weflow.kr domain allowed)
- SQL Injection prevention (JPA/QueryDSL)
- XSS prevention

## Database

### Key ERD Entities
- **User**: Users
- **Company**: Companies (Agency/Client)
- **Project**: Projects
- **ProjectMember**: Project members
- **Step**: Project steps
- **StepRequest**: Approval requests
- **Post**: Posts
- **Comment**: Comments
- **Checklist**: Checklists
- **Notification**: Notifications
- **ActivityLog**: Activity logs

### Key Features
- Soft Delete (deletedAt field)
- Audit logs (BaseEntity: createdAt, updatedAt)
- Cursor pagination (Performance optimization)
- Index optimization

## Development Guide

### Commit Conventions
- `feat`: Add new feature
- `fix`: Bug fix
- `docs`: Documentation updates (README, etc.)
- `style`: Code formatting, semicolons, etc. (No logic changes)
- `refactor`: Code refactoring (No functional changes)
- `test`: Add/modify test code
- `chore`: Build/deployment/package configuration changes

### Branch Strategy
- `main`: Production deployment branch
- `develop`: Development branch
- `feature/*`: Feature development
- `fix/*`: Bug fixes
- `hotfix/*`: Emergency fixes
- `refactor/*`: Refactoring
- `chore/*`: Configuration changes

### Pull Request
1. Work on feature branch
2. Create PR to develop branch
3. Code review and merge
4. Release from develop to main

## CI/CD

- **GitHub Actions** for automated build and deployment
- Triggers on push to `develop` branch
- Automated deployment to AWS EC2 via Docker & ECR

## Testing

```bash
# Run all tests
./gradlew test

# Run specific test
./gradlew test --tests TestClassName
```

## License

This project is proprietary.

## Team

FastCampus KDT BackEnd 13th Final Project - Team BN-3
