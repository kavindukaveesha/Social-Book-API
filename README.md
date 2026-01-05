# 📚 Social Book Network API

A comprehensive Spring Boot REST API for a social book network platform where users can manage books, share reviews, and connect with fellow readers. Built with modern Java ecosystem and enterprise-grade architecture patterns.

## 🚀 Features

- **User Authentication**: JWT-based secure authentication with email verification
- **Book Management**: Complete CRUD operations with advanced filtering and pagination
- **Book Sharing**: Secure book borrowing and lending system
- **Feedback System**: Rating and review system with comprehensive feedback management
- **User Libraries**: Personal book collections with ownership tracking
- **File Management**: Book cover upload with image processing capabilities
- **Security**: Role-based access control with enterprise security features

## 🛠️ Technology Stack

### Core Framework
- **Java 17** - Latest LTS with modern language features
- **Spring Boot 3.2.2** - Enterprise application framework
- **Spring Security 6.2.1** - Comprehensive security framework
- **Spring Data JPA** - Advanced data persistence layer

### Database & Storage
- **PostgreSQL** - Robust relational database system
- **Hibernate** - ORM with advanced entity relationships
- **Connection Pooling** - Optimized database performance

### Development Tools
- **Maven 3.11.0** - Build automation and dependency management
- **Lombok** - Code generation for reduced boilerplate
- **SpringDoc OpenAPI** - Automated API documentation
- **JWT Authentication** - Stateless security implementation

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+

## ⚙️ Installation & Setup

### 🏠 Local Development Setup

#### 1. Clone Repository
```bash
git clone <repository-url>
cd Social-Book-API
```

#### 2. Prerequisites Installation

**Install Java 17:**
```bash
# macOS (using Homebrew)
brew install openjdk@17

# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-17-jdk

# Windows (using Chocolatey)
choco install openjdk17
```

**Install Maven:**
```bash
# macOS
brew install maven

# Ubuntu/Debian
sudo apt install maven

# Windows
choco install maven
```

**Install PostgreSQL:**
```bash
# macOS
brew install postgresql
brew services start postgresql

# Ubuntu/Debian
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql

# Windows
# Download from https://www.postgresql.org/download/windows/
```

#### 3. Database Setup
```bash
# Create PostgreSQL user and database
sudo -u postgres psql

# In PostgreSQL shell:
CREATE USER bookuser WITH PASSWORD 'bookpassword';
CREATE DATABASE bookDb OWNER bookuser;
GRANT ALL PRIVILEGES ON DATABASE bookDb TO bookuser;
\q

# Alternative: Using createdb command
createdb -O bookuser bookDb
```

#### 4. Application Configuration
Update `src/main/resources/application-dev.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bookDb
    username: bookuser
    password: bookpassword
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
  mail:
    host: smtp.gmail.com
    port: 587
    username: your-email@gmail.com
    password: your-app-password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

#### 5. Environment Variables (Optional)
Create `.env` file in project root:
```bash
DB_URL=jdbc:postgresql://localhost:5432/bookDb
DB_USERNAME=bookuser
DB_PASSWORD=bookpassword
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password
JWT_SECRET=your-jwt-secret-key
```

#### 6. Build and Run
```bash
# Verify Java and Maven installation
java -version
mvn -version

# Clean and compile
mvn clean compile

# Run tests
mvn test

# Build the project
mvn clean package -DskipTests

# Run in development mode
java -jar target/book-network-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev

# Alternative: Maven run (recommended for development)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Run with custom port
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080
```

### 🐳 Docker Setup

#### Option 1: Docker Compose (Recommended)

Create `docker-compose.yml` in project root:
```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15-alpine
    container_name: social-book-postgres
    environment:
      POSTGRES_DB: bookDb
      POSTGRES_USER: bookuser
      POSTGRES_PASSWORD: bookpassword
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    networks:
      - social-book-network

  app:
    build: .
    container_name: social-book-api
    depends_on:
      - postgres
    ports:
      - "8088:8088"
    environment:
      SPRING_PROFILES_ACTIVE: docker
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/bookDb
      SPRING_DATASOURCE_USERNAME: bookuser
      SPRING_DATASOURCE_PASSWORD: bookpassword
    volumes:
      - app_uploads:/app/uploads
    networks:
      - social-book-network

volumes:
  postgres_data:
  app_uploads:

networks:
  social-book-network:
    driver: bridge
```

Create `Dockerfile`:
```dockerfile
FROM openjdk:17-jdk-alpine

# Install curl for healthcheck
RUN apk add --no-cache curl

# Set working directory
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Give execution permission to mvnw
RUN chmod +x ./mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build application
RUN ./mvnw clean package -DskipTests

# Create uploads directory
RUN mkdir -p /app/uploads

# Expose port
EXPOSE 8088

# Add healthcheck
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8088/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "target/book-network-0.0.1-SNAPSHOT.jar"]
```

Create `application-docker.yml`:
```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:postgresql://postgres:5432/bookDb}
    username: ${SPRING_DATASOURCE_USERNAME:bookuser}
    password: ${SPRING_DATASOURCE_PASSWORD:bookpassword}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
  file:
    uploads-photos-path: /app/uploads
server:
  port: 8088
```

**Run with Docker Compose:**
```bash
# Build and start all services
docker-compose up --build

# Run in detached mode
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

#### Option 2: Separate Docker Commands

**Run PostgreSQL:**
```bash
docker run --name social-book-postgres \
  -e POSTGRES_DB=bookDb \
  -e POSTGRES_USER=bookuser \
  -e POSTGRES_PASSWORD=bookpassword \
  -p 5432:5432 \
  -v postgres_data:/var/lib/postgresql/data \
  -d postgres:15-alpine
```

**Build Application:**
```bash
# Build Docker image
docker build -t social-book-api .

# Run application
docker run --name social-book-app \
  -p 8088:8088 \
  -e SPRING_PROFILES_ACTIVE=docker \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/bookDb \
  -e SPRING_DATASOURCE_USERNAME=bookuser \
  -e SPRING_DATASOURCE_PASSWORD=bookpassword \
  --link social-book-postgres:postgres \
  -d social-book-api
```

#### Docker Development Workflow
```bash
# Development with hot reload
docker-compose -f docker-compose.dev.yml up

# Production build
docker-compose -f docker-compose.prod.yml up --build

# Scale application
docker-compose up --scale app=3

# Database backup
docker exec social-book-postgres pg_dump -U bookuser bookDb > backup.sql

# Restore database
docker exec -i social-book-postgres psql -U bookuser bookDb < backup.sql
```

### 🚀 Quick Start Commands

**Local Development:**
```bash
# Complete setup in one go
git clone <repository-url> && cd Social-Book-API
mvn clean package -DskipTests
mvn spring-boot:run
```

**Docker Quick Start:**
```bash
# Complete Docker setup
git clone <repository-url> && cd Social-Book-API
docker-compose up --build
```

### 📊 Verification

After setup, verify the application is running:

1. **Health Check**: http://localhost:8088/actuator/health
2. **API Documentation**: http://localhost:8088/api/v1/swagger-ui.html
3. **Database Connection**: Check application logs for successful connection

### 🔧 Troubleshooting

**Common Issues:**

1. **Port Already in Use:**
   ```bash
   # Find process using port
   lsof -i :8088
   # Kill process
   kill -9 <PID>
   ```

2. **Database Connection Failed:**
   ```bash
   # Check PostgreSQL status
   sudo systemctl status postgresql
   # Restart PostgreSQL
   sudo systemctl restart postgresql
   ```

3. **Memory Issues:**
   ```bash
   # Increase JVM heap size
   export MAVEN_OPTS="-Xmx1024m"
   mvn spring-boot:run
   ```

4. **Docker Issues:**
   ```bash
   # Clean Docker system
   docker system prune -a
   # Rebuild without cache
   docker-compose build --no-cache
   ```

## 📖 API Documentation

Interactive API documentation available at:
- **Swagger UI**: http://localhost:8088/api/v1/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8088/api/v1/v3/api-docs

![Swagger UI](images/swagger.png)

## 🔐 Authentication Flow

### Register New User
```bash
curl -X POST http://localhost:8088/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "John",
    "lastname": "Doe", 
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8088/api/v1/auth/authentication \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

### Access Protected Resources
```bash
curl -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  http://localhost:8088/api/v1/books
```

## 🛠️ API Endpoints

### Authentication & Authorization
- `POST /api/v1/auth/register` - User registration with email verification
- `POST /api/v1/auth/authentication` - JWT-based login system
- `GET /api/v1/auth/activate-account` - Email-based account activation

### Book Management
- `GET /api/v1/books` - Paginated book listing with filtering
- `GET /api/v1/books/{id}` - Retrieve individual book details
- `POST /api/v1/books` - Create new books with validation
- `GET /api/v1/books/owner` - User's owned books management
- `GET /api/v1/books/borrowed` - Borrowed books tracking
- `POST /api/v1/books/borrow/{id}` - Borrow a book
- `PATCH /api/v1/books/borrow/return/{id}` - Return a borrowed book
- `PATCH /api/v1/books/shareable/{id}` - Update sharing permissions
- `POST /api/v1/books/cover/{id}` - Upload book cover (max 50MB)

### Feedback & Reviews
- `POST /api/v1/feedbacks` - Submit book reviews and ratings
- `GET /api/v1/feedbacks/book/{id}` - Paginated feedback retrieval

## 🗄️ Database Architecture

### Core Entities
- **Users** (`_user` table) - User management with Spring Security integration
- **Books** (`book` table) - Advanced entity with builder pattern
- **Feedbacks** (`feedback` table) - Rating and comment management
- **Book Transaction History** (`book_transaction_history` table) - Borrowing tracking
- **Roles** (`role` table) - Role-based access control
- **Tokens** (`token` table) - JWT token management

### Advanced Features
- **Audit Trail** - BaseEntity with creation/modification tracking
- **Soft Deletes** - Logical deletion with archived status
- **Complex Relationships** - One-to-many and many-to-many associations
- **Custom Specifications** - Dynamic query building with JPA Criteria API

## 🔒 Security Features

- **JWT Token Management** - Stateless authentication with refresh tokens
- **BCrypt Password Encryption** - Industry-standard password hashing
- **Role-Based Access Control** - Hierarchical permission system
- **CORS Configuration** - Secure cross-origin resource sharing
- **Input Validation** - Jakarta validation with custom error handling
- **SQL Injection Prevention** - Parameterized queries and ORM protection

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run with coverage report
mvn test jacoco:report

# Run specific test class
mvn test -Dtest=BookControllerTest
```

## ☁️ AWS Deployment Guide

This section provides a comprehensive guide to deploy the Social Book Network API on AWS using EC2, RDS PostgreSQL, and automated CI/CD with GitHub Actions.

### 🏗️ AWS Infrastructure Setup

#### 1. Database Setup (Amazon RDS PostgreSQL)

**Step 1: Create RDS PostgreSQL Instance**

![Database Creation](images/db_connect.png)

1. Navigate to Amazon RDS console
2. Click "Create database"
3. Choose PostgreSQL engine
4. Select instance specifications:
   - **DB Instance Class**: `db.t4g.micro` (Free Tier eligible)
   - **Engine Version**: PostgreSQL 17.6
   - **Storage**: 20 GB General Purpose SSD (gp2)
   - **Multi-AZ**: Disabled for cost optimization

**Step 2: Configure Database Settings**

![Database Configuration](images/db_details.png)

```yaml
Database Configuration:
- DB Instance ID: springboot-db
- Master Username: postgres
- Master Password: [Your secure password]
- Database Name: springboot-db
- Port: 5432
- VPC: Default VPC
- Public Access: Yes (for development)
- VPC Security Groups: Create new security group
```

**Step 3: Verify Database Connection**

![Database Success](images/db_success.png)

Once created, note down the database endpoint:
```
springboot-db.cjk8okk2a65q.us-east-2.rds.amazonaws.com:5432
```

#### 2. EC2 Instance Setup

**Step 1: Launch EC2 Instance**

![EC2 Instance](images/ec2.png)

1. Navigate to EC2 console and launch new instance:
   - **AMI**: Amazon Linux 2023
   - **Instance Type**: `t3.micro` (Free Tier eligible)
   - **Key Pair**: Create or use existing key pair
   - **Storage**: 8 GB General Purpose SSD

**Step 2: Configure Security Groups**

![Security Groups](images/security_groups.png)

Create security group with the following inbound rules:

![Security Rules](images/ec2_security_rules.png)

| Type | Protocol | Port Range | Source | Description |
|------|----------|------------|---------|-------------|
| SSH | TCP | 22 | My IP | SSH access |
| HTTP | TCP | 80 | 0.0.0.0/0 | HTTP traffic |
| HTTPS | TCP | 443 | 0.0.0.0/0 | HTTPS traffic |
| Custom TCP | TCP | 8080 | 0.0.0.0/0 | Spring Boot Default |

#### 3. Server Configuration

**Step 1: Connect to EC2 Instance**

![Server Command Line](images/server_cl.png)

```bash
# Connect via SSH
ssh -i your-key.pem ec2-user@your-ec2-public-ip

# Update system packages
sudo yum update -y

# Install Java 17
sudo yum install java-17-openjdk java-17-openjdk-devel -y

# Install Maven
sudo yum install maven -y

# Install Git
sudo yum install git -y

# Install Nginx (for reverse proxy)
sudo yum install nginx -y
```

**Step 2: Configure Nginx Reverse Proxy**

![Nginx Configuration](images/nginix.png)

Create Nginx configuration:
```bash
sudo nano /etc/nginx/conf.d/springboot.conf
```

```nginx
server {
    listen 80;
    server_name your-domain.com;  # Replace with your domain or public IP
    
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

Start Nginx:
```bash
sudo systemctl enable nginx
sudo systemctl start nginx
```

### 🚀 Application Deployment

#### 1. Manual Deployment

```bash
# Clone repository
git clone https://github.com/kavindukaveesha/Social-Book-API.git
cd Social-Book-API

# Create application.properties for production
sudo nano src/main/resources/application-prod.yml
```

```yaml
spring:
  datasource:
    url: jdbc:postgresql://springboot-db.cjk8okk2a65q.us-east-2.rds.amazonaws.com:5432/springboot-db
    username: postgres
    password: ${DB_PASSWORD}
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
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

server:
  port: 8080

file:
  uploads-photos-path: /app/uploads

logging:
  level:
    com.NextCoreInv.book_network: INFO
```

```bash
# Build application
mvn clean package -DskipTests

# Run application
nohup java -jar target/book-network-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=prod \
  --DB_PASSWORD=your_db_password \
  --MAIL_USERNAME=your_email \
  --MAIL_PASSWORD=your_app_password > app.log 2>&1 &
```

#### 2. Automated Deployment with GitHub Actions

**Step 1: Configure GitHub Secrets**

![GitHub Secrets](images/github_secrets.png)

Add the following secrets in your GitHub repository:
- `EC2_HOST`: Your EC2 instance public IP
- `EC2_USER`: `ec2-user`
- `EC2_SSH_KEY`: Your private key content

**Step 2: GitHub Actions Workflow**

![GitHub Actions](images/github_actions.png)

The repository includes automated deployment workflow:

![GitHub Actions Success](images/github_actions_success.png)

`.github/workflows/deploy.yml`:
```yaml
name: Deploy to EC2

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'adopt'
    
    - name: Build with Maven
      run: mvn clean package -DskipTests
    
    - name: Deploy to EC2
      uses: appleboy/ssh-action@v0.1.5
      with:
        host: ${{ secrets.EC2_HOST }}
        username: ${{ secrets.EC2_USER }}
        key: ${{ secrets.EC2_SSH_KEY }}
        script: |
          cd /home/ec2-user/Social-Book-API
          git pull origin main
          mvn clean package -DskipTests
          sudo systemctl restart springboot
```

### 🔧 Production Configuration

#### 1. Environment Variables

```bash
# Create environment file
sudo nano /etc/environment
```

```bash
DB_PASSWORD=your_secure_db_password
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
JWT_SECRET=your_jwt_secret_key
UPLOADS_PATH=/opt/springboot/uploads
```

#### 2. Systemd Service Configuration

```bash
# Create systemd service
sudo nano /etc/systemd/system/springboot.service
```

```ini
[Unit]
Description=Spring Boot Application
After=network.target

[Service]
Type=forking
User=ec2-user
WorkingDirectory=/home/ec2-user/Social-Book-API
ExecStart=/usr/bin/java -jar target/book-network-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5
EnvironmentFile=/etc/environment

[Install]
WantedBy=multi-user.target
```

```bash
# Enable and start service
sudo systemctl daemon-reload
sudo systemctl enable springboot
sudo systemctl start springboot
```

### 📊 Verification & Testing

#### 1. Health Check

![Test Swagger API](images/test_swagger.png)

Access the health endpoint:
```bash
curl http://your-ec2-public-ip/api/v1/test
```

#### 2. API Documentation

![Swagger Documentation](images/swagger copy.png)

Access Swagger UI at:
```
http://your-ec2-public-ip/api/v1/swagger-ui.html
```

#### 3. Database Connection Test

Verify database connectivity:
```bash
# Connect to RDS from EC2
psql -h springboot-db.cjk8okk2a65q.us-east-2.rds.amazonaws.com \
     -p 5432 \
     -U postgres \
     -d springboot-db
```

### 🔒 Security Best Practices

#### 1. Database Security
- Enable encryption at rest
- Use VPC security groups
- Restrict public access
- Regular backup scheduling
- Monitor database performance

#### 2. EC2 Security
- Regular security updates
- Use IAM roles instead of access keys
- Enable CloudWatch monitoring
- Implement fail2ban for SSH protection
- Use SSL certificates (Let's Encrypt)

#### 3. Application Security
- Environment-based configuration
- Secure JWT secret management
- Input validation and sanitization
- Rate limiting implementation
- CORS configuration

### 🚨 Monitoring & Maintenance

#### 1. CloudWatch Integration
```bash
# Install CloudWatch agent
wget https://s3.amazonaws.com/amazoncloudwatch-agent/amazon_linux/amd64/latest/amazon-cloudwatch-agent.rpm
sudo rpm -U ./amazon-cloudwatch-agent.rpm
```

#### 2. Log Management
```bash
# Application logs
tail -f /home/ec2-user/Social-Book-API/app.log

# Nginx logs
sudo tail -f /var/log/nginx/access.log
sudo tail -f /var/log/nginx/error.log

# System logs
sudo journalctl -u springboot -f
```

#### 3. Backup Strategy
```bash
# Automated database backup
pg_dump -h springboot-db.cjk8okk2a65q.us-east-2.rds.amazonaws.com \
        -U postgres \
        -d springboot-db > backup_$(date +%Y%m%d_%H%M%S).sql
```

### 💰 Cost Optimization

1. **Use Free Tier Resources**: t3.micro EC2 and db.t4g.micro RDS
2. **Reserved Instances**: For long-term deployments
3. **Auto Scaling**: Based on demand
4. **CloudWatch Alarms**: Monitor usage and costs
5. **Scheduled Shutdowns**: For development environments

### 🔗 Quick Access URLs

After successful deployment:
- **Application Health**: `http://your-ec2-ip/api/v1/test`
- **API Documentation**: `http://your-ec2-ip/api/v1/swagger-ui.html`
- **Admin Dashboard**: `http://your-ec2-ip/admin` (if configured)

## 📦 Production Deployment

```bash
# Create production build
mvn clean package -Pprod

# Run in production mode
java -jar target/book-network-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## 📁 Project Structure

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
│   │   ├── file/          # File Management
│   │   └── common/        # Shared Components
│   └── resources/
│       ├── application.yml        # Main configuration
│       ├── application-dev.yml    # Development settings
│       └── application-prod.yml   # Production settings
└── test/                  # Comprehensive Test Suite
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines
- Follow Java coding standards and Spring Boot best practices
- Write comprehensive tests for new features
- Update API documentation for endpoint changes
- Ensure security considerations for all implementations

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Kavindu Kaveesha**
- Email: kavindukaveesha16@gmail.com
- Website: https://kavindukaveesha.com
- GitHub: [@kavindukaveesha](https://github.com/kavindukaveesha)

## 🙏 Acknowledgments

- Spring Boot community for the robust framework
- PostgreSQL team for the reliable database system
- JWT.io community for authentication standards
- OpenAPI/Swagger for comprehensive API documentation
- All contributors and the open-source community

---

*Built with Spring Boot 3.2.2 • Java 17 • PostgreSQL • JWT Authentication*