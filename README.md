# Study Case: Paginated API Service Using External Data Source

Spring Boot REST API that implements server-side pagination for external data sources.

## Tech Stack

- **Java 21** (LTS)
- **Spring Boot 3.5.10**
- **Maven**
- **RestClient** for HTTP calls
- **Caffeine Cache** for performance optimization
- **Lombok** for reducing boilerplate
- **Swagger/OpenAPI** for API documentation

## Features

- ✅ Paginated user listing with customizable page size
- ✅ Data fetching from external mock API (DummyJSON)
- ✅ Comprehensive error handling with proper HTTP status codes
- ✅ Input validation for pagination parameters
- ✅ Caching mechanism with 10-minute TTL
- ✅ Swagger/OpenAPI documentation
- ✅ Clean layered architecture (Controller → Service → Client)
- ✅ Java 21 Records for immutable DTOs
- ✅ Proper logging with SLF4J

## How to Run

### Prerequisites

- Java 21 or higher
- Maven 3.9+

### Steps

```bash
# Navigate to project directory
cd pagination

# Run application
./mvnw spring-boot:run

# Access API
curl http://localhost:8080/api/users?page=1&size=10
```

The application will start on `http://localhost:8080`

## API Documentation

### Get Paginated Users

**Endpoint:** `GET /api/users`

**Query Parameters:**
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| page | int | 1 | Page number (starts from 1) |
| size | int | 10 | Number of items per page |

**Get first page with default size (10 items):**

```bash
GET /api/users
```

**Success Response (200 OK):**

```json
{
  "page": 1,
  "size": 10,
  "totalItems": 100,
  "totalPages": 10,
  "data": [
    {
      "id": 1,
      "firstName": "Emily",
      "lastName": "Johnson",
      "age": 29,
      "email": "emily.johnson@x.dummyjson.com",
      "phone": "+81 965-431-3024",
      "username": "emilys",
      ...
    }
  ]
}
```

**Get second page with 5 items per page:**

```bash
GET /api/users?page=2&size=5
```

**Success Response (200 OK):**

```json
{
  "page": 2,
  "size": 5,
  "totalItems": 100,
  "totalPages": 20,
  "data": [
    {
      "id": 6,
      "firstName": "Olivia",
      "lastName": "Wilson",
      "age": 23,
      "email": "olivia.wilson@x.dummyjson.com",
      "phone": "+91 607-295-6448",
      "username": "oliviaw",
      ...
    }
  ]
}
```

**Get users filtered by name:**

```bash
GET /api/users?name=John
```

**Success Response (200 OK):**

```json
{
  "page": 1,
  "size": 10,
  "totalItems": 1,
  "totalPages": 1,
  "data": [
    {
      "id": 1,
      "firstName": "Emily",
      "lastName": "Johnson",
      "age": 29,
      "email": "emily.johnson@x.dummyjson.com",
      "phone": "+81 965-431-3024",
      "username": "emilys",
      ...
    }
  ]
}
```

**Get users filtered by username:**

```bash
GET /api/users?username=levih
```

**Success Response (200 OK):**

```json
{
  "page": 1,
  "size": 10,
  "totalItems": 1,
  "totalPages": 1,
  "data": [
    {
      "id": 91,
      "firstName": "Levi",
      "lastName": "Hicks",
      "age": 30,
      "email": "levi.hicks@x.dummyjson.com",
      "phone": "+92 931-753-3850",
      "username": "levih",
      ...
    }
  ]
}
```

**Get users filtered by username:**

```bash
GET /api/users?page=0&size=10
```

**Error Response (400 Bad Request):**

```json
{
  "timestamp": "2026-01-24T10:23:00Z",
  "status": 400,
  "error": "Invalid pagination parameter",
  "message": "Page must be greater than 0"
}
```

**Error Response (500 Internal Server Error):**

```json
{
  "timestamp": "2026-01-24T10:23:00Z",
  "status": 500,
  "error": "External API Error",
  "message": "Unable to fetch data from external source"
}
```

## Architecture

### Package Structure

```
src/main/java/com/example/pagination/
├── controller/
│   └── UserController.java          # REST endpoints
├── service/
│   ├── UserService.java             # Service interface
│   ├── UserCacheService.java        # Cache service interface
│   └── impl/
│       ├── UserServiceImpl.java     # Service implementation
│       └── UserCacheServiceImpl.java # Cache service implementation
├── client/
│   ├── ExternalApiClient.java       # Client interface
│   └── impl/ExternalApiClientImpl.java # Client implementation
├── model/
│   └── User.java                    # Domain model (Record)
├── dto/
│   ├── UserQuery.java               # Query object (Record)
│   ├── PaginationResponse.java      # Response wrapper (Record)
│   ├── ErrorResponse.java           # Error response (Record)
│   └── ExternalApiResponse.java     # External API DTO (Record)
├── specification/
│   ├── UserSpecification.java       # Specification interface
│   ├── UserSpecificationFactory.java # Specification builder
│   ├── NameSpecification.java       # Filter by name logic
│   └── UsernameSpecification.java   # Filter by username logic
├── exception/
│   ├── GlobalExceptionHandler.java  # Global error handler
│   └── ExternalApiException.java    # Custom exception
└── config/
    ├── RestClientConfig.java        # HTTP client config
    └── CacheConfig.java             # Cache configuration
```

### Design Principles Applied

- **Dependency Inversion:** Services depend on abstractions (interfaces)
- **Single Responsibility:** Each class has one clear purpose
- **Open/Closed:** Easy to extend without modifying existing code
- **Separation of Concerns:** Clear boundaries between layers

### Why Java 21?

- **Records:** Immutable DTOs with minimal boilerplate
- **Pattern Matching:** Cleaner exception handling
- **Modern Syntax:** More expressive and readable code
- **LTS Release:** Long-term support and stability

## 🔧 Configuration

Edit `src/main/resources/application.yaml` to customize:

```yaml
server:
  port: 8080

external:
  api:
    base-url: https://dummyjson.com
    users-endpoint: /users
    timeout: 5000

pagination:
  default-page: 1
  default-size: 10
  max-size: 100

cache:
  ttl-seconds: 600 # 10 minutes
```

## Testing

### Manual Testing Checklist

- [x] GET /api/users (default params) → 200 OK
- [x] GET /api/users?page=1&size=10 → correct data
- [x] GET /api/users?page=2&size=5 → correct pagination
- [x] GET /api/users?page=0 → 400 Bad Request
- [x] GET /api/users?size=0 → 400 Bad Request
- [x] GET /api/users?page=-1 → 400 Bad Request
- [x] GET /api/users?page=100 → 200 OK with empty data
- [x] Cache working (second request faster)

### Test Commands

```bash
# Test default parameters
curl "http://localhost:8080/api/users"

# Test filter by name
curl "http://localhost:8080/api/users?name=John"

# Test filter by username
curl "http://localhost:8080/api/users?username=john"

# Test pagination
curl "http://localhost:8080/api/users?page=2&size=5"

# Test invalid page (should return 400)
curl "http://localhost:8080/api/users?page=0"

# Test invalid size (should return 400)
curl "http://localhost:8080/api/users?size=0"

# Test page beyond total (should return empty data)
curl "http://localhost:8080/api/users?page=100"
```

## Swagger UI

Access interactive API documentation at:

```
http://localhost:8080/swagger-ui/index.html
```

## Performance Optimizations

- **Caching:** External API responses cached for 10 minutes using Caffeine
- **Efficient Slicing:** Sublist operations for pagination
- **Lazy Loading:** Data fetched only when needed
- **Connection Pooling:** RestClient manages HTTP connections efficiently

## Implementation Highlights

### 1. Pagination Logic

```java
int startIndex = (page - 1) * size;
int endIndex = Math.min(startIndex + size, totalItems);
int totalPages = (int) Math.ceil((double) totalItems / size);
```

### 2. Caching Strategy

Uses Spring Cache with Caffeine for automatic caching:

- TTL: 10 minutes (configurable)
- Cache key: Method name
- Eviction: Time-based

### 3. Error Handling

Global exception handler catches:

- `IllegalArgumentException` → 400 Bad Request
- `ExternalApiException` → 500 Internal Server Error
- `Exception` → 500 Internal Server Error

## Author

Dian Rahmat Nataatmaja Hadi Sugandi [@drnhsproject](https://github.com/drnhsproject)
