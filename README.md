# Learning Kotlin - CRUD API

A simple RESTful CRUD (Create, Read, Update, Delete) API built with Kotlin and Ktor framework. This project demonstrates the basics of building a web API using Kotlin, including project setup, dependency management, and running Kotlin applications.

## Table of Contents

- [Introduction to Kotlin Projects](#introduction-to-kotlin-projects)
- [Project Structure](#project-structure)
- [Virtual Environments](#virtual-environments)
- [Dependencies Management](#dependencies-management)
- [How to Run the Code](#how-to-run-the-code)
- [How to Compile the Code](#how-to-compile-the-code)
- [CLI Commands](#cli-commands)
- [CRUD API Description](#crud-api-description)
- [API Endpoints](#api-endpoints)
- [Testing the API](#testing-the-api)

## Introduction to Kotlin Projects

Kotlin is a modern, statically-typed programming language that runs on the JVM (Java Virtual Machine). It's fully interoperable with Java and is the preferred language for Android development. Kotlin can also be used for:

- Server-side development (backend APIs, web applications)
- Desktop applications
- Multi-platform mobile development
- Data science

Kotlin projects typically use build tools like **Gradle** or **Maven** to manage dependencies, compilation, and execution. This project uses **Gradle** with Kotlin DSL (Domain-Specific Language).

### Key Features of Kotlin:
- Concise syntax with less boilerplate
- Null safety built into the type system
- Coroutines for asynchronous programming
- Extension functions
- Data classes
- Functional programming support

## Project Structure

```
kotlin-crud-api/
├── build.gradle.kts          # Gradle build configuration (Kotlin DSL)
├── settings.gradle.kts        # Gradle settings file
├── gradlew                    # Gradle wrapper script (Unix)
├── gradlew.bat               # Gradle wrapper script (Windows)
├── gradle/                   # Gradle wrapper files
│   └── wrapper/
├── src/
│   └── main/
│       └── kotlin/
│           └── com/
│               └── example/
│                   ├── Application.kt           # Main application entry point
│                   ├── models/
│                   │   └── Item.kt             # Data model
│                   └── routes/
│                       └── ItemRoutes.kt       # API routes/endpoints
└── README.md
```

## Virtual Environments

Unlike Python, Kotlin/JVM projects don't use virtual environments in the same way. Instead:

1. **JDK (Java Development Kit)**: You need a JDK installed on your system. This project uses JDK 17.
   
2. **Gradle Wrapper**: The included `gradlew` script ensures everyone uses the same Gradle version, eliminating "works on my machine" issues. It automatically downloads the correct Gradle version if needed.

3. **Dependencies Isolation**: Each project's dependencies are managed through Gradle and stored separately in the Gradle cache (`~/.gradle/`).

### Installing JDK

- **Linux (Ubuntu/Debian)**:
  ```bash
  sudo apt update
  sudo apt install openjdk-17-jdk
  ```

- **macOS** (using Homebrew):
  ```bash
  brew install openjdk@17
  ```

- **Windows**: Download from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/)

Verify installation:
```bash
java -version
```

## Dependencies Management

Dependencies are declared in `build.gradle.kts` using Gradle's dependency management system.

### Main Dependencies in this Project:

```kotlin
dependencies {
    // Ktor Server - Web framework
    implementation("io.ktor:ktor-server-core:2.3.5")
    implementation("io.ktor:ktor-server-netty:2.3.5")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
    
    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.11")
    
    // Testing
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host:2.3.5")
}
```

### Adding New Dependencies:

1. Open `build.gradle.kts`
2. Add the dependency in the `dependencies` block
3. Sync/reload the project (IDE) or run `./gradlew build`

Dependencies are downloaded from Maven Central repository automatically.

## How to Run the Code

### Using Gradle Wrapper (Recommended):

```bash
# On Linux/macOS
./gradlew run

# On Windows
gradlew.bat run
```

The server will start on `http://localhost:8080`

### Using a Compiled JAR:

```bash
# Build the JAR
./gradlew build

# Run the JAR
java -jar build/libs/kotlin-crud-api-1.0.0.jar
```

### From an IDE (IntelliJ IDEA, VS Code):

1. Open the project in your IDE
2. Navigate to `src/main/kotlin/com/example/Application.kt`
3. Click the green "Run" button next to the `main` function

## How to Compile the Code

### Compile without running:

```bash
./gradlew build
```

This will:
- Download dependencies (first time only)
- Compile Kotlin code to JVM bytecode
- Run tests (if any)
- Create executable JARs in `build/libs/`

### Compile without tests:

```bash
./gradlew build -x test
```

### Clean and rebuild:

```bash
./gradlew clean build
```

## CLI Commands

Here are the most common Gradle commands for Kotlin projects:

### Build and Compilation:
```bash
./gradlew build           # Compile code and run tests
./gradlew clean           # Delete build directory
./gradlew assemble        # Compile without running tests
./gradlew jar             # Create JAR file
```

### Running:
```bash
./gradlew run             # Run the application
./gradlew run --args="arg1 arg2"  # Run with arguments
```

### Dependencies:
```bash
./gradlew dependencies    # Show dependency tree
./gradlew dependencyUpdates  # Check for updates (requires plugin)
```

### Development:
```bash
./gradlew classes         # Compile main classes only
./gradlew compileKotlin   # Compile Kotlin code only
```

### Testing:
```bash
./gradlew test            # Run tests
./gradlew test --tests "ClassName.methodName"  # Run specific test
```

### Information:
```bash
./gradlew tasks           # List all available tasks
./gradlew projects        # Show project structure
./gradlew help            # Show help
```

### Performance:
```bash
./gradlew build --no-daemon    # Build without Gradle daemon
./gradlew build --parallel     # Enable parallel execution
```

## CRUD API Description

This is a simple RESTful API that manages a collection of **Items**. Each item has:

- **id**: Unique integer identifier
- **name**: String name of the item
- **description**: String description
- **price**: Double price value

The API uses **in-memory storage** (data is lost when the server stops). It demonstrates:

- RESTful API design principles
- JSON serialization/deserialization
- HTTP methods (GET, POST, PUT, DELETE)
- HTTP status codes
- Route parameters
- Request/response handling

### Technology Stack:

- **Kotlin 2.0.21**: Programming language
- **Ktor 2.3.5**: Lightweight web framework
- **kotlinx.serialization**: JSON serialization
- **Netty**: Embedded web server
- **Logback**: Logging framework

## API Endpoints

### Base URL
```
http://localhost:8080
```

### Welcome Endpoint

**GET /** - Welcome message
```bash
curl http://localhost:8080/
```

Response:
```
Welcome to Kotlin CRUD API! Use /items endpoints to manage items.
```

### Get All Items

**GET /items** - Retrieve all items

```bash
curl http://localhost:8080/items
```

Response (200 OK):
```json
[
  {
    "id": 1,
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99
  },
  {
    "id": 2,
    "name": "Mouse",
    "description": "Wireless mouse",
    "price": 29.99
  },
  {
    "id": 3,
    "name": "Keyboard",
    "description": "Mechanical keyboard",
    "price": 79.99
  }
]
```

### Get Item by ID

**GET /items/{id}** - Retrieve a specific item

```bash
curl http://localhost:8080/items/1
```

Response (200 OK):
```json
{
  "id": 1,
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 999.99
}
```

Error Response (404 Not Found):
```json
"Item not found"
```

### Create New Item

**POST /items** - Create a new item

```bash
curl -X POST http://localhost:8080/items \
  -H "Content-Type: application/json" \
  -d '{
    "id": 4,
    "name": "Monitor",
    "description": "4K Monitor",
    "price": 399.99
  }'
```

Response (201 Created):
```json
{
  "id": 4,
  "name": "Monitor",
  "description": "4K Monitor",
  "price": 399.99
}
```

Error Response (409 Conflict):
```json
"Item with this ID already exists"
```

### Update Item

**PUT /items/{id}** - Update an existing item

```bash
curl -X PUT http://localhost:8080/items/1 \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "name": "Gaming Laptop",
    "description": "High-end gaming laptop",
    "price": 1299.99
  }'
```

Response (200 OK):
```json
{
  "id": 1,
  "name": "Gaming Laptop",
  "description": "High-end gaming laptop",
  "price": 1299.99
}
```

Error Response (404 Not Found):
```json
"Item not found"
```

### Delete Item

**DELETE /items/{id}** - Delete an item

```bash
curl -X DELETE http://localhost:8080/items/1
```

Response (204 No Content): Empty response

Error Response (404 Not Found):
```json
"Item not found"
```

## Testing the API

### Using curl (Command Line):

See examples above in each endpoint section.

### Using HTTPie (User-friendly alternative to curl):

```bash
# Install HTTPie
pip install httpie

# Get all items
http GET localhost:8080/items

# Create item
http POST localhost:8080/items id:=5 name="Headphones" description="Noise-cancelling" price:=199.99

# Update item
http PUT localhost:8080/items/5 id:=5 name="Premium Headphones" description="Noise-cancelling wireless" price:=249.99

# Delete item
http DELETE localhost:8080/items/5
```

### Using Postman or Insomnia:

1. Download [Postman](https://www.postman.com/) or [Insomnia](https://insomnia.rest/)
2. Create requests for each endpoint
3. Set appropriate HTTP methods and headers
4. Add JSON body for POST/PUT requests

### Using a Web Browser:

For GET requests only:
- `http://localhost:8080/`
- `http://localhost:8080/items`
- `http://localhost:8080/items/1`

## Development Tips

### Hot Reload / Auto-Restart:

For development, you might want to use Gradle's continuous build:

```bash
./gradlew run --continuous
```

Or use IntelliJ IDEA's built-in run configuration with automatic reload.

### Logging:

The application uses Logback for logging. Check console output for request logs.

### Error Handling:

The API returns appropriate HTTP status codes:
- **200 OK**: Successful GET/PUT
- **201 Created**: Successful POST
- **204 No Content**: Successful DELETE
- **400 Bad Request**: Invalid input
- **404 Not Found**: Resource not found
- **409 Conflict**: Resource already exists

## Next Steps

To extend this project, consider:

1. **Add database persistence** (PostgreSQL, MySQL, MongoDB)
2. **Add authentication/authorization** (JWT tokens)
3. **Add input validation** (using kotlinx.validation or similar)
4. **Add unit tests** (using Kotlin Test or JUnit)
5. **Add API documentation** (OpenAPI/Swagger)
6. **Dockerize the application**
7. **Add pagination for large datasets**
8. **Implement filtering and sorting**

## License

This project is created for learning purposes.

## Resources

- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Ktor Documentation](https://ktor.io/docs/)
- [Gradle Documentation](https://docs.gradle.org/)
- [Kotlin Style Guide](https://kotlinlang.org/docs/coding-conventions.html)
