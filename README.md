# Property Rental Platform

This project is a full-stack property rental platform designed to connect tenants with landlords. It provides a seamless experience for users to find and list various properties, including rooms, studios, apartments, and houses. The platform features robust user authentication, a comprehensive advert management system, and real-time chat functionality.

## Features

- **User Authentication:** Secure registration and login for both tenants and landlords using email/password or Google OAuth2.
- **Profile Management:** Users can update their personal information, security settings, and link/unlink their Google accounts.
- **Advert Management (for Landlords):**
    - Create, update, and delete property listings with detailed descriptions and multiple photos.
    - View and manage all created adverts from a central dashboard.
- **Property Search & Discovery (for Tenants):**
    - Advanced search functionality with filters for city, price range, property type, and furnishing.
    - View search results in a paginated list or on an interactive map (powered by Leaflet).
    - Save favorite adverts for later viewing.
- **Real-time Communication:**
    - Integrated WebSocket-based chat (using STOMP) for instant communication between tenants and landlords.
- **Data & Insights:**
    - View city-level statistics, including the number of listings, average price, and average property size.
    - See a showcase of the most popular adverts on the homepage.
- **API Documentation:** The backend includes Swagger UI for easy exploration and testing of the REST API.

## Technology Stack

| Category      | Technology                                                                                                 |
| ------------- | ---------------------------------------------------------------------------------------------------------- |
| **Frontend**  | React.js, Vite, Redux Toolkit, Redux Persist, React Router, Axios, Bootstrap, CSS Modules |
| **Backend**   | Java 17, Spring Boot, Spring Security, Spring Data JPA, Hibernate, Spring WebSocket (STOMP), JWT, jBCrypt |
| **Database**  | MySQL, Flyway                                                                                              |
| **Testing**   | Cypress (E2E), Mockito                                                                                               |
| **DevOps**    | Docker, Gradle                                                                                              |
| **Documentation** | Swagger / OpenAPI                                                                                     |

## Local Development Setup

To run this project locally, you will need Java 17, Node.js, and a running MySQL instance.

### 1. Backend Setup

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/emiliasparuhova/property-rental-platform.git
    cd property-rental-platform/backend
    ```

2.  **Configure the database:**
    - Create a MySQL database named `individual_sem3`.
    - Update the database credentials in `backend/src/main/resources/application.properties`:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/individual_sem3
      spring.datasource.username=your_mysql_username
      spring.datasource.password=your_mysql_password
      ```

3.  **Configure API Keys:**
    - In the same `application.properties` file, provide the necessary API keys for JWT, Google OAuth, and external services.

4.  **Run the application:**
    - The application uses Flyway for database migrations, which will run automatically on startup.
    - You can run the application using your IDE or by building it with Gradle:
      ```bash
      ./gradlew build
      java -jar build/libs/individual-project-backend-0.0.1-SNAPSHOT.jar
      ```
    - The backend server will start on `http://localhost:8080`.

### 2. Frontend Setup

1.  **Navigate to the frontend directory:**
    ```bash
    cd ../frontend
    ```

2.  **Install dependencies:**
    ```bash
    npm install
    ```

3.  **Run the development server:**
    ```bash
    npm run dev
    ```
    - The frontend application will be available at `http://localhost:5173`.

### API Documentation (Swagger)

Once the backend is running, you can access the Swagger UI for detailed API documentation at:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
