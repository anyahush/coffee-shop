# Coffee Shop Supply Management

A full-stack Java (Spring Boot) + React application to track and manage coffee-shop supplies.

---

## 📖 Table of Contents

1. [Project Overview](#project-overview)
2. [Prerequisites](#prerequisites)
3. [Getting the Code](#getting-the-code)
4. [Backend Setup & Run](#backend-setup--run)
5. [Frontend Setup & Run](#frontend-setup--run)
6. [Running Tests](#running-tests)
7. [API Documentation (Swagger)](#api-documentation-swagger)
8. [Project Structure](#project-structure)

---

## 🚀 Project Overview

This system provides:

- **CRUD** operations on supply items
- **Search** by name, category or supplier
- **Reorder list** generation for low-stock items
- **CSV import** of bulk supplies
- **In-memory H2 database** for quick setup
- **Swagger/OpenAPI** docs for REST endpoints

The UI is built with React + Bootstrap, and the backend uses Spring Boot.

---

## 🛠 Prerequisites

- **Java 17+**
- **Maven 3.6+** (or Gradle if you prefer)
- **Node.js 16+** & **npm 8+**
- Git client to clone the repository

---

## 📥 Getting the Code

### 1. Clone the Repo

  ```bash
   git clone <project-http>.git
   cd coffee-shop
   ```
---

### 2. Backend (Spring Boot)

1. **Build** — download dependencies & compile:

   ```bash
   mvn clean package
   ```
2. **Run** the application:

   ```bash
   mvn spring-boot:run
   ```

   The API is now listening on port **8080**.

---

### 3. Frontend (React)

1. **Enter** the frontend folder:

   ```bash
   cd frontend
   ```
2. **Install** npm packages:

   ```bash
   npm install
   ```
3. **Start** the development server:

   ```bash
   npm start
   ```
4. **Open** your browser to:

   ```
   http://localhost:3000
   ```

   All `/api/...` calls will be proxied to your backend on port 8080.

---

### 4. Running Tests

* **Backend tests** (unit + integration):

  ```bash
  # from project root
  mvn test
  ```
* **Frontend tests** (Jest & React Testing Library):

  ```bash
  # from frontend/ directory
  npm test
  ```

## 📂 Project Structure

```text
coffee-shop-supply/
├── .gitignore
├── pom.xml                   # Backend build & dependencies
├── src/
│   ├── main/
│   │   ├── java/…           # Spring Boot application code
│   │   └── resources/…      # application.properties, etc.
│   └── test/
│       └── java/…           # Backend tests
└── frontend/
    ├── package.json         # Frontend build & dependencies
    ├── public/              # CRA static assets
    └── src/
        ├── api/             # Axios wrappers
        ├── components/      # Reusable UI components
        ├── pages/           # Route-based views
        └── setupTests.js    # Jest & RTL setup
