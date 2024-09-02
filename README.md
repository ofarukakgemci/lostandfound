# Lost & Found Application

## Overview

This application provides a platform for users to claim lost items and allows admins to manage and upload lost items. It includes features such as caching, pagination, and integration with a mock OAuth server.

## Application Setup

### Prerequisites

- Gradle: For building and running the application.
- Docker Compose: For running Redis, PostgreSQL, and a mock OAuth server. Ensure you have docker-compose installed.
- **Java 21**: This project requires Java 21. Make sure you have it installed.

## Setting Up Java 21

To ensure that `gradlew` uses Java 21, setting the `JAVA_HOME` environment variable is enough.
### On macOS/Linux:

1. **Open Terminal**.
2. **Set JAVA_HOME**:
    - Run the following command:
      ```bash
      export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home
      ```
    - (The exact path may vary depending on your installation location.)

### Running the Application

Setup Dependencies: Ensure that Redis, PostgreSQL, and the mock OAuth server are up and running. 
Use the provided docker-compose-dev.yml file.

```sh

docker-compose -f docker-compose-dev.yml up

```
#### Build and Run the Application:

```bash

./gradlew build
./gradlew bootRun

````

#### Using IntelliJ IDEA
 - Import the Project:

 - Open IntelliJ IDEA.
 - Go to File -> Open.
 - Select the root directory of the project and open it.

IntelliJ will automatically detect the Gradle build system and import the project.


The application will be available at http://localhost:8080

#### Run Tests:

```bash

./gradlew test

````

#### Testing in Docker Container

To run tests in a Docker container, use the following command:

For macOS Users
```bash

docker run --rm -v $(pwd):/app -w /app eclipse-temurin:21-alpine ./gradlew test --no-daemon
 
```

For Windows Users
```bash

docker run --rm -v ${pwd}:/app -w /app eclipse-temurin:21-alpine ./gradlew test --no-daemon

```

### Swagger UI

While the application is running, you can explore and test the APIs using Swagger UI.

- Swagger UI URL: http://localhost:8080/swagger-ui.html

### How to Use Swagger UI
- Access Swagger UI: Open the URL in your web browser.
- Authorize: 
  - Click on the "Authorize" button. 
  - Select the openid scope and enter a random client-id to obtain a JWT token.
  - When Mock OAuth2 Server Sign-in page appears write a random username and click sign-in.
  - If your username starts with 'admin' you are granted admin role.
  - Test Endpoints: Use the Swagger UI to interact with various endpoints by filling in the required parameters and submitting requests.

## API Endpoints

1. Upload Lost Items
   - Method: POST
   - Endpoint: /admin/upload-lost-items
   - Description: Upload lost items from a file. The file type is specified via the fileType parameter. Supported file types are: text, pdf.
2. Get Lost Items
   - Method: GET
   - Endpoint: /lost-item
   - Description: Retrieve a paginated list of all lost items. Pagination parameters include page and size.
3. Claim a Lost Item
   - Method: POST
   - Endpoint: /claim
   - Description: Allows a user to claim a specific lost item. Requires lostItemId and quantity parameters.
4. Get User Claims
   - Method: GET
   - Endpoint: /claim
   - Description: Retrieve a paginated list of items claimed by the current user. Pagination parameters include page and size.
5. Get All Claims (Admin)
   - Method: GET
   - Endpoint: /admin/claim
   - Description: Retrieve a paginated list of all claims made by users. Pagination parameters include page and size.
6. Get Current User Information
   - Method: GET
   - Endpoint: /auth/me
   - Description: Retrieve information about the currently authenticated user.

## Notes & Future Improvements

   - Caching: Redis is used to cache frequently accessed lost items for improved performance and scalability.
   - Object-Oriented Design: The application utilizes object-oriented patterns and is structured to be extendable for future enhancements.
   - File Type Handling: Improve handling of various file types.
   - OAuth Integration: Transition from mock OAuth to a fully-featured OAuth service.
   - The application has been designed with scalability and performance in mind.
   - Due to time constraints, the project could benefit from further improvements and enhancements in various areas.
   - The codebase could be refined to address additional edge cases and strengthen design patterns.
   - The Swagger documentation provided can be enriched for better clarity and usability.
   - Full implementation of CI/CD pipelines; automating build, testing, and deployment processes would facilitate more consistent and reliable releases.
   - Implementing monitoring and logging solutions, such as Actuator with Grafana, would enhance performance tracking and log management.
   - API versioning through Git tags and Docker builds to effectively manage different API versions and their deployments.
   - Breaking down the smaller, independent services.
   - Increasing test coverage to handle more edge cases.
