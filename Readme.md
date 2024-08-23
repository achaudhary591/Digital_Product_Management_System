# Digital Product Management System

## Overview
This project consists of two Spring Boot microservices designed to manage a digital product inventory, including CRUD operations and price updates. The main service handles product management, while the secondary service manages product price updates. The services communicate via REST APIs.

## Technology Stack
- Java 11
- Spring Boot
- MongoDB
- Maven
- Spring Data MongoDB
- Spring Web
- Spring Boot Starter Validation
- RestTemplate (for inter-service communication)
- Lombok (for boilerplate code reduction)
- JUnit & Mockito (for testing)

## Services

### 1. Product Management Service
This service handles the core CRUD operations for digital products.

#### Key Endpoints:
- `GET /products` - Retrieve all products.
- `GET /products/{product_id}` - Retrieve a specific product by ID.
- `POST /products` - Create a new product.
- `PUT /products/{product_id}` - Update an existing product.
- `DELETE /products/{product_id}` - Delete a product by ID.

#### Configuration:
Located in `src/main/resources/application.properties`:
```properties
server.port=8080
spring.data.mongodb.database=productdb
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
```

### 2. Price Update Service
This service receives requests to update product prices and communicates with the Product Management Service to apply those updates.

#### Key Endpoint:
- `POST /price-update` - Update the price of a product.

#### Configuration:
Located in `src/main/resources/application.properties`:
```properties
server.port=8081
product.management.service.url=http://localhost:8080/products
```

### 3. RestTemplate Configuration
Ensure that the `RestTemplate` bean is configured in the Price Update Service to enable communication between the services.

**RestTemplateConfig.java**
```java
package com.example.priceupdateservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

## Setup Instructions

### Prerequisites
- Java 11+
- Maven
- MongoDB
- An IDE (IntelliJ IDEA, Eclipse, etc.)

### Running MongoDB
Ensure MongoDB is installed and running on your local machine:
```bash
mongod --dbpath /path/to/your/db
```

### Running the Services

1. **Clone the repository:**
    ```bash
    git clone <repository_url>
    cd digital-product-management-system
    ```

2. **Run the Product Management Service:**
    ```bash
    cd product-management-service
    mvn clean install
    mvn spring-boot:run
    ```

3. **Run the Price Update Service:**
    ```bash
    cd price-update-service
    mvn clean install
    mvn spring-boot:run
    ```

### Example API Requests

#### Create a Product
```bash
curl -X POST http://localhost:8080/products \
-H "Content-Type: application/json" \
-d '{"name":"Product1","description":"Description1","price":100,"available":true}'
```

#### Update Product Price
```bash
curl -X POST http://localhost:8081/price-update \
-H "Content-Type: application/json" \
-d '{"productId":"{PRODUCT_ID}","newPrice":150}'
```

## Testing

Both services include unit tests that can be run using Maven:
```bash
mvn test
```

## Future Enhancements
- Implement authentication mechanisms (e.g., JWT).
- Add filtering and search capabilities to the `GET /products` endpoint.
- Set up Docker for containerized deployment.
- Integrate with a message broker like Kafka for asynchronous updates.

## Conclusion
This project demonstrates how to build a simple, scalable microservices-based system using Spring Boot and Java, with MongoDB as the data store. The separation of concerns between product management and price updates ensures modularity and easier maintenance.
