# 📚 Social Book Network API

A comprehensive Spring Boot REST API for a social book network platform where users can manage books, share reviews, and connect with fellow readers.

## 🚀 Features

- **User Authentication**: JWT-based secure authentication
- **Book Management**: CRUD operations for books with pagination
- **Book Sharing**: Users can share and borrow books
- **Feedback System**: Rate and review books
- **User Libraries**: Personal book collections management
- **Secure APIs**: Role-based access control

## 🛠️ Technology Stack

- **Java 17**
- **Spring Boot 3.2.2**
- **Spring Security 6.2.1**
- **Spring Data JPA**
- **PostgreSQL**
- **JWT Authentication**
- **Maven**
- **OpenAPI/Swagger Documentation**

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+

## ⚙️ Installation & Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Social-Book-API
   ```

2. **Configure Database**
   ```bash
   # Create PostgreSQL database
   createdb bookDb
   ```

3. **Update Configuration**
   ```yaml
   # src/main/resources/application-dev.yml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/bookDb
       username: your_username
       password: your_password
   ```

4. **Build and Run**
   ```bash
   # Build the project
   mvn clean package -DskipTests
   
   # Run the application
   java -jar target/book-network-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
   ```

## 📖 API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: http://localhost:8088/api/v1/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8088/api/v1/v3/api-docs

## 🔐 Authentication

### Register a new user
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

### Access Protected Endpoints
```bash
curl -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  http://localhost:8088/api/v1/books
```

## 🛠️ Main Endpoints

### Authentication
- `POST /api/v1/auth/register` - Register new user
- `POST /api/v1/auth/authentication` - User login
- `GET /api/v1/auth/activate-account` - Activate user account

### Books
- `GET /api/v1/books` - Get all books (paginated)
- `GET /api/v1/books/{id}` - Get book by ID
- `POST /api/v1/books` - Create new book
- `GET /api/v1/books/owner` - Get user's books
- `GET /api/v1/books/borrowed` - Get borrowed books
- `POST /api/v1/books/borrow/{id}` - Borrow a book
- `PATCH /api/v1/books/borrow/return/{id}` - Return a book

### Feedbacks
- `POST /api/v1/feedbacks` - Create book feedback
- `GET /api/v1/feedbacks/book/{id}` - Get book feedbacks

## 🗄️ Database Schema

The application uses PostgreSQL with the following main entities:
- **Users** (_user table)
- **Books** (book table)
- **Feedbacks** (feedback table)
- **Book Transaction History** (book_transaction_history table)
- **Roles** (role table)
- **Tokens** (token table)

## 🔒 Security Features

- JWT token-based authentication
- BCrypt password encryption
- Role-based access control
- CORS configuration
- Input validation
- SQL injection prevention

## 🧪 Testing

```bash
# Run tests
mvn test

# Run with coverage
mvn test jacoco:report
```

## 📦 Build for Production

```bash
# Create production build
mvn clean package -Pprod

# Run in production mode
java -jar target/book-network-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Kavindu Kaveesha**
- Email: kavindukaveesha16@gmail.com
- Website: https://kavindukaveesha.com

## 🙏 Acknowledgments

- Spring Boot community
- OpenAPI/Swagger documentation
- PostgreSQL database
- JWT authentication library