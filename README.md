# Blood Donation Management System

A full-stack web application for managing blood donations, donor records, blood inventory, and usage tracking. Built with Spring Boot and a single-page application frontend, this system has including blood banks to efficiently register donors, record donations, monitor available blood stock, and log blood usage.

---

## Table of Contents

1. [About / Overview](#about--overview)
2. [Technologies Used](#technologies-used)
3. [Project Structure](#project-structure)
4. [Installation and Setup](#installation-and-setup)
5. [Usage](#usage)
6. [Database Schema](#database-schema)
7. [API Endpoints](#api-endpoints)
8. [Deployment](#deployment)
9. [Future Improvements](#future-improvements)
10. [AI Acknowledgment](#ai-acknowledgment)
11. [License](#license)

---

## About / Overview

The Blood Donation Management System is designed to help blood banks and healthcare organizations manage their day-to-day operations. The application provides a centralized platform for:

- **Donor Management** -- Register, update, search, and remove donor records.
- **Blood Donation Tracking** -- Record individual donations linked to donors with blood type and quantity.
- **Inventory Monitoring** -- View real-time blood inventory calculated as the difference between total donations and total usage per blood type.
- **Blood Usage Logging** -- Record blood usage with stock validation to prevent over-dispensing.
- **Dashboard** -- View summary statistics including total donors, total donations, total blood used, and current inventory levels.
- **Authentication** -- Secure access using JWT-based authentication. A single hard-coded admin account is created on startup.

The system supports eight blood types: A+, A-, B+, B-, AB+, AB-, O+, and O-.

### Current Limitations

- **No role-based access control.** The User entity defines ADMIN and STAFF roles, but no authorization rules are enforced. All authenticated users have identical access to every endpoint and feature. Role-based access control is planned for a future release.
- **Hard-coded admin account.** The only way to log in is with the admin credentials that are created automatically on application startup (`admin@bloodbank.com` / `admin123`). There is no user management interface or self-registration.

---

## Technologies Used

| Category        | Technology                                      |
|-----------------|-------------------------------------------------|
| Language        | Java 21                                         |
| Framework       | Spring Boot 4.0.2                               |
| Security        | Spring Security with JWT (JJWT 0.12.6)          |
| Database        | MySQL with Spring Data JPA (Hibernate)          |
| Validation      | Jakarta Bean Validation                         |
| Build Tool      | Apache Maven                                    |
| Code Generation | Lombok 1.18.40                                  |
| Frontend        | HTML5, CSS3, JavaScript                         |
| Dev Tools       | Spring Boot DevTools (live reload)              |
| Deployment      | Railway                                         |
---

## Link to Railway
https://blooddonation.up.railway.app/

## Project Structure

```
Blood-Donation-System/
├── pom.xml                          # Maven build configuration
└── src/
    └── main/
        ├── java/com/bloodbank/
        │   ├── BloodBankApplication.java       # Main application entry point
        │   ├── config/
        │   │   ├── DataInitializer.java        # Creates default admin user on startup
        │   │   ├── JwtAuthFilter.java          # JWT authentication filter
        │   │   ├── JwtService.java             # JWT token generation and validation
        │   │   ├── SecurityConfig.java         # Spring Security and CORS configuration
        │   │   └── WebConfig.java              # Static file serving and SPA routing
        │   ├── controller/
        │   │   ├── AuthController.java         # Authentication endpoint
        │   │   ├── BloodDonationController.java# Blood donation endpoints
        │   │   ├── BloodUsageController.java   # Blood usage and inventory endpoints
        │   │   ├── DashboardController.java    # Dashboard statistics endpoint
        │   │   └── DonorController.java        # Donor management endpoints
        │   ├── dto/
        │   │   ├── request/                    # Request DTOs with validation
        │   │   └── response/                   # Response DTOs
        │   ├── entity/
        │   │   ├── BloodDonation.java          # Blood donation entity
        │   │   ├── BloodUsage.java             # Blood usage entity
        │   │   ├── Donor.java                  # Donor entity with BloodType enum
        │   │   └── User.java                   # User entity implementing UserDetails
        │   ├── exception/
        │   │   ├── GlobalExceptionHandler.java # Centralized error handling
        │   │   ├── InsufficientStockException.java
        │   │   └── ResourceNotFoundException.java
        │   ├── repository/                     # Spring Data JPA repositories
        │   └── service/                        # Business logic services
        └── resources/
            ├── application.properties          # Application configuration
            ├── schema.sql                      # Database schema reference
            └── static/
                └── index.html                  # Single-page application frontend
```

---

## Installation and Setup

### Prerequisites

- **Java 21** or later
- **Apache Maven** 3.8 or later
- **MySQL** 8.0 or later

### Step 1 -- Clone the Repository

```bash
git clone https://github.com/lweweyfyohla/Blood-Donation-System.git
cd Blood-Donation-System
```

### Step 2 -- Create the MySQL Database

Connect to your MySQL server and create a database:

```sql
CREATE DATABASE bloodbank;
```

### Step 3 -- Configure Environment Variables

The application reads its configuration from environment variables. Set the following before running:

| Variable            | Description                        | Example Value                            |
|---------------------|------------------------------------|------------------------------------------|
| `MYSQLHOST`         | MySQL server hostname              | `localhost`                              |
| `MYSQLPORT`         | MySQL server port                  | `3306`                                   |
| `MYSQLDATABASE`     | Database name                      | `bloodbank`                              |
| `MYSQLUSER`         | Database username                  | `root`                                   |
| `MYSQLPASSWORD`     | Database password                  | `example: 1234`                               |
| `APP_JWT_SECRET`    | Secret key for JWT signing         | `A long random string (minimum 32 chars)` |
| `APP_FRONTEND_URL`  | Allowed CORS origin (optional)     | `http://localhost:8080`                  |
| `PORT`              | Server port (optional, default 8080) | `8080`                                 |

On Linux or macOS:

```bash
export MYSQLHOST=localhost
export MYSQLPORT=3306
export MYSQLDATABASE=bloodbank
export MYSQLUSER=root
export MYSQLPASSWORD=yourpassword
export APP_JWT_SECRET=YourSuperSecretKeyAtLeast32Characters
```

On Windows (Command Prompt):

```cmd
set MYSQLHOST=localhost
set MYSQLPORT=3306
set MYSQLDATABASE=bloodbank
set MYSQLUSER=root
set MYSQLPASSWORD=yourpassword
set APP_JWT_SECRET=YourSuperSecretKeyAtLeast32Characters
```

### Step 4 -- Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080` (or the port specified by the `PORT` variable). Hibernate will automatically create the required tables on first startup.

### Default Admin Credentials

On first run, a hard-coded admin account is created by the `DataInitializer`. This is the only user account available; there is currently no way to create additional users through the application.

- **Email:** `admin@bloodbank.com`
- **Password:** `admin123`

These credentials are defined directly in the source code. It is strongly recommended to change them if deploying to a production environment.

---

## Usage

1. Open a web browser and navigate to `http://localhost:8080`.
2. Log in using the hard-coded admin credentials (see [Installation and Setup](#installation-and-setup) for details).
3. Use the navigation menu to access the following sections:
   - **Sing In** -- Sing in with email and password.
   - **Dashboard** -- View summary statistics and current inventory levels.
   - **Donors** -- Add, edit, search, and delete donor records.
   - **Donations** -- Record new blood donations linked to registered donors.
   - **Inventory** -- View real-time blood stock levels per blood type.
   - **Usage** -- Record blood usage with notes; the system validates available stock before confirming.
5. The frontend is a single-page application served directly by the Spring Boot backend. No separate frontend server is required.

---

## Database Schema

The system uses four tables. The inventory is not stored in a dedicated table; instead, it is calculated at runtime as the difference between total donations and total usage per blood type.

### Tables

#### users

| Column       | Type          | Constraints                        |
|-------------|---------------|------------------------------------|
| id          | BIGINT        | PRIMARY KEY, AUTO_INCREMENT        |
| name        | VARCHAR(150)  | NOT NULL                           |
| email       | VARCHAR(255)  | NOT NULL, UNIQUE                   |
| password    | VARCHAR(255)  | NOT NULL (BCrypt hashed)           |
| role        | VARCHAR(20)   | NOT NULL, DEFAULT 'STAFF' (not enforced at authorization level) |
| created_at  | TIMESTAMP     | NOT NULL, DEFAULT CURRENT_TIMESTAMP|

#### donors

| Column       | Type          | Constraints                        |
|-------------|---------------|------------------------------------|
| id          | BIGINT        | PRIMARY KEY, AUTO_INCREMENT        |
| name        | VARCHAR(150)  | NOT NULL                           |
| phone       | VARCHAR(20)   | NOT NULL                           |
| blood_type  | VARCHAR(10)   | NOT NULL                           |
| email       | VARCHAR(255)  |                                    |
| created_at  | TIMESTAMP     | NOT NULL, DEFAULT CURRENT_TIMESTAMP|

#### blood_donations

| Column         | Type          | Constraints                           |
|---------------|---------------|---------------------------------------|
| id            | BIGINT        | PRIMARY KEY, AUTO_INCREMENT           |
| donor_id      | BIGINT        | NOT NULL, FOREIGN KEY -> donors(id)   |
| blood_type    | VARCHAR(10)   | NOT NULL                              |
| quantity      | INT           | NOT NULL                              |
| donation_date | DATE          | NOT NULL                              |
| created_at    | TIMESTAMP     | NOT NULL, DEFAULT CURRENT_TIMESTAMP   |

#### blood_usages

| Column       | Type          | Constraints                        |
|-------------|---------------|------------------------------------|
| id          | BIGINT        | PRIMARY KEY, AUTO_INCREMENT        |
| blood_type  | VARCHAR(10)   | NOT NULL                           |
| quantity    | INT           | NOT NULL                           |
| used_date   | DATE          | NOT NULL                           |
| note        | VARCHAR(500)  |                                    |
| created_at  | TIMESTAMP     | NOT NULL, DEFAULT CURRENT_TIMESTAMP|

### Relationships

```
users               (standalone -- used for authentication)

donors ──< blood_donations
  One donor can have many blood donations.
  blood_donations.donor_id references donors.id

blood_usages        (standalone -- tracks blood consumed)

Inventory = SUM(blood_donations.quantity) - SUM(blood_usages.quantity) per blood_type
```

### Supported Blood Types

`A_POS`, `A_NEG`, `B_POS`, `B_NEG`, `AB_POS`, `AB_NEG`, `O_POS`, `O_NEG`

---

## API Endpoints

All API endpoints (except `/api/auth/login`) require a valid JWT token in the `Authorization` header:

```
Authorization: Bearer <token>
```

### Authentication

| Method | Endpoint          | Description                  |
|--------|-------------------|------------------------------|
| POST   | `/api/auth/login` | Authenticate and receive JWT |

**Request body:**

```json
{
  "email": "admin@bloodbank.com",
  "password": "admin123"
}
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "name": "Admin",
  "role": "ADMIN"
}
```

Note: The `role` field is returned in the response but is not currently used for authorization. All authenticated users have the same access level.

### Donors

| Method | Endpoint                              | Description                          |
|--------|---------------------------------------|--------------------------------------|
| GET    | `/api/donors`                         | List all donors (supports `search`, `page`, `size` query params) |
| GET    | `/api/donors/{id}`                    | Get a donor by ID                    |
| POST   | `/api/donors`                         | Create a new donor                   |
| PUT    | `/api/donors/{id}`                    | Update an existing donor             |
| DELETE | `/api/donors/{id}`                    | Delete a donor                       |
| GET    | `/api/donors/{id}/donations`          | Get all donations for a donor        |
| GET    | `/api/donors/blood-type/{bloodType}`  | Get donors by blood type             |

### Blood Donations

| Method | Endpoint              | Description                   |
|--------|-----------------------|-------------------------------|
| GET    | `/api/donations`      | List all donations             |
| GET    | `/api/donations/{id}` | Get a donation by ID           |
| POST   | `/api/donations`      | Record a new blood donation    |

### Blood Usage

| Method | Endpoint                          | Description                               |
|--------|-----------------------------------|-------------------------------------------|
| GET    | `/api/usages`                     | List all blood usage records               |
| POST   | `/api/usages`                     | Record blood usage (validates stock)       |
| GET    | `/api/usages/inventory`           | Get inventory for all blood types          |
| GET    | `/api/usages/inventory/{bloodType}` | Get inventory for a specific blood type  |

### Dashboard

| Method | Endpoint          | Description                                |
|--------|-------------------|--------------------------------------------|
| GET    | `/api/dashboard`  | Get summary statistics and inventory data  |

**Response:**

```json
{
  "totalDonors": 25,
  "totalDonations": 120,
  "totalBloodUsed": 80,
  "inventory": [
    {
      "bloodType": "A_POS",
      "donated": 30,
      "used": 15,
      "available": 15
    }
  ]
}
```

---

## Deployment

### Railway

The application is configured to work with Railway out of the box. To deploy:

1. Connect the GitHub repository to a new Railway project.
2. Add a MySQL service to the project. Railway will automatically provision the database and set the required environment variables (`MYSQLHOST`, `MYSQLPORT`, `MYSQLDATABASE`, `MYSQLUSER`, `MYSQLPASSWORD`).
3. Set the following additional environment variables in the Railway service settings:
   - `APP_JWT_SECRET` -- A secure random string for JWT signing.
   - `APP_FRONTEND_URL` -- The deployed frontend URL for CORS (or `*` to allow all origins).
4. Railway will detect the Maven project, build it, and start the application automatically.

### General Deployment

To build and run the application as a standalone JAR:

```bash
mvn clean package -DskipTests
java -jar target/bloodbank-2.0.0.jar
```

Ensure all required environment variables are set before starting the JAR.

---

## Future Improvements

- **Role-Based Access Control** -- Enforce the existing ADMIN and STAFF roles so that different users have different permissions. The role infrastructure already exists in the User entity but is not checked by the security configuration (see [Current Limitations](#current-limitations)).
- **User Management Interface** -- Add a dedicated UI and API for administrators to create, update, and deactivate user accounts instead of relying on a single hard-coded admin (see [Current Limitations](#current-limitations)).
- **Blood Request Workflow** -- Allow hospitals or departments to submit blood requests that must be approved before usage is recorded.
- **Reporting and Analytics** -- Generate downloadable reports on donation trends, usage patterns, and inventory forecasts.
- **Notification System** -- Send alerts when inventory for a blood type falls below a configurable threshold.
- **Donor History View** -- Display a detailed donation history timeline for each donor.
- **Audit Logging** -- Record all create, update, and delete operations for compliance and traceability.
- **Pagination in Frontend** -- Implement full pagination support in the frontend for large datasets.
- **Containerization** -- Provide a Dockerfile and Docker Compose setup for simplified local development and deployment.

---

## AI Acknowledgment

AI tools were used to assist in generating the README.md, debugging parts of the code, and helping with configuration during the development process.

---

## License

This project is open source and available for educational purposes.
