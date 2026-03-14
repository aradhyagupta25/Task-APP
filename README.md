# Tasks App

A full-stack task management application built with **Spring Boot** (backend) and **React + TypeScript** (frontend). Supports creating task lists, managing tasks with priorities and due dates, and tracking completion progress.

---

## Table of Contents

- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [1. Clone the Repository](#1-clone-the-repository)
  - [2. Start the Database](#2-start-the-database)
  - [3. Run the Backend](#3-run-the-backend)
  - [4. Run the Frontend](#4-run-the-frontend)
- [Environment Configuration](#environment-configuration)
- [API Reference](#api-reference)
  - [Task Lists](#task-lists)
  - [Tasks](#tasks)
- [Frontend Overview](#frontend-overview)
- [Domain Model](#domain-model)
- [Known Issues & Fixes Applied](#known-issues--fixes-applied)

---

## Tech Stack

| Layer     | Technology                                      |
|-----------|-------------------------------------------------|
| Backend   | Java 21, Spring Boot 3.2.5, Spring Data JPA     |
| Database  | PostgreSQL (via Docker)                         |
| ORM       | Hibernate 6.4                                   |
| Frontend  | React 18, TypeScript, Vite                      |
| UI        | NextUI, Tailwind CSS, Framer Motion             |
| HTTP      | Axios                                           |
| Routing   | React Router v6                                 |
| Container | Docker, Docker Compose                          |

---

## Project Structure

```
tasks/
├── src/
│   └── main/java/com/aradhyagupta25/tasks/
│       ├── controllers/         # REST controllers
│       │   ├── TaskController.java
│       │   ├── TaskListController.java
│       │   └── GlobalExceptionHandler.java
│       ├── domain/
│       │   ├── dto/             # Data Transfer Objects
│       │   └── entities/        # JPA Entities
│       ├── mappers/             # DTO <-> Entity mappers
│       │   └── impl/
│       ├── repositories/        # Spring Data JPA repositories
│       └── services/            # Business logic
│           └── impl/
├── src/test/                    # Tests with H2 in-memory DB
├── docker-compose.yml           # PostgreSQL container
├── application.properties       # App configuration
└── tasks-fe/                    # React frontend
    ├── src/
    │   ├── components/          # UI screens
    │   ├── domain/              # TypeScript interfaces & enums
    │   ├── App.tsx
    │   ├── AppProvider.tsx      # Global state + API layer
    │   └── main.tsx
    ├── vite.config.ts           # Dev proxy config
    └── package.json
```

---

## Prerequisites

Make sure you have the following installed:

- [Java 21](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [Node.js 22+](https://nodejs.org/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

---

## Getting Started

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd tasks
```

### 2. Start the Database

The app uses PostgreSQL running inside Docker. Start it with:

```bash
docker compose up -d
```

This spins up a container named `tasks-postgres` on port `5432` with:

| Setting  | Value           |
|----------|-----------------|
| Database | `tasks`         |
| Username | `postgres`      |
| Password | `changemeinprod!` |

> ⚠️ If you have a local PostgreSQL installation also running on port 5432, stop it first via `services.msc` on Windows before starting Docker.

Verify the container is running:

```bash
docker ps
```

### 3. Run the Backend

```bash
./mvnw spring-boot:run
```

The backend starts on **http://localhost:8080**.

Spring Boot will auto-create the required database tables on first run (`spring.jpa.hibernate.ddl-auto=update`).

### 4. Run the Frontend

```bash
cd tasks-fe
npm install
npm run dev
```

The frontend starts on **http://localhost:5173**.

Vite proxies all `/api/*` requests to `http://localhost:8080`, so no CORS configuration is needed during development.

---

## Environment Configuration

Backend config lives in `src/main/resources/application.properties`:

```properties
spring.application.name=tasks

spring.datasource.url=jdbc:postgresql://localhost:5432/tasks
spring.datasource.username=postgres
spring.datasource.password=changemeinprod!
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.properties.hibernate.jdbc.time_zone=Asia/Kolkata
spring.jpa.hibernate.ddl-auto=update

# For detailed error messages during development
server.error.include-message=always
server.error.include-binding-errors=always
```

A separate test config at `src/test/resources/application.properties` uses an **H2 in-memory database** so tests run without Docker.

---

## API Reference

Base URL: `http://localhost:8080`

### Task Lists

| Method | Endpoint                      | Description            |
|--------|-------------------------------|------------------------|
| GET    | `/api/task-lists`             | Get all task lists     |
| POST   | `/api/task-lists`             | Create a task list     |
| GET    | `/api/task-lists/{id}`        | Get a task list by ID  |
| PUT    | `/api/task-lists/{id}`        | Update a task list     |
| DELETE | `/api/task-lists/{id}`        | Delete a task list     |

**TaskList request body:**
```json
{
  "title": "My Task List",
  "description": "Optional description"
}
```

### Tasks

| Method | Endpoint                                          | Description         |
|--------|---------------------------------------------------|---------------------|
| GET    | `/api/task-lists/{listId}/tasks`                  | Get all tasks       |
| POST   | `/api/task-lists/{listId}/tasks`                  | Create a task       |
| GET    | `/api/task-lists/{listId}/tasks/{taskId}`         | Get a task by ID    |
| PUT    | `/api/task-lists/{listId}/tasks/{taskId}`         | Update a task       |
| DELETE | `/api/task-lists/{listId}/tasks/{taskId}`         | Delete a task       |

**Task request body:**
```json
{
  "title": "Fix the bug",
  "description": "Optional description",
  "dueDate": "2026-03-20T10:00:00",
  "priority": "HIGH",
  "status": "OPEN"
}
```

**Enums:**
- `priority`: `HIGH`, `MEDIUM`, `LOW`
- `status`: `OPEN`, `CLOSED`

---

## Frontend Overview

| Screen                     | Route                                      | Description                        |
|----------------------------|--------------------------------------------|------------------------------------|
| Task Lists                 | `/`                                        | View all task lists with progress  |
| Create Task List           | `/new-task-list`                           | Form to create a new task list     |
| Edit Task List             | `/edit-task-list/:listId`                  | Form to update a task list         |
| Tasks                      | `/task-lists/:listId`                      | View tasks in a list               |
| Create Task                | `/task-lists/:listId/new-task`             | Form to create a new task          |
| Edit Task                  | `/task-lists/:listId/edit-task/:taskId`    | Form to update a task              |

Global state and API calls are managed in `AppProvider.tsx` using React's `useReducer` hook, giving a Redux-like architecture without any external state library.

---

## Domain Model

```
TaskList
├── id         UUID
├── title      String (required)
├── description String
├── created    LocalDateTime
├── updated    LocalDateTime
└── tasks      List<Task>

Task
├── id          UUID
├── title       String (required)
├── description String
├── dueDate     LocalDateTime
├── status      TaskStatus (OPEN | CLOSED)
├── priority    TaskPriority (HIGH | MEDIUM | LOW)
├── created     LocalDateTime
├── updated     LocalDateTime
└── taskList    TaskList (FK)
```

---

## Known Issues & Fixes Applied

| Issue | Fix |
|-------|-----|
| `PSQLException: password authentication failed` | Local PostgreSQL was stealing port 5432. Stop local PostgreSQL service before running Docker. |
| `Ambiguous mapping` on startup | `updateTask` in `TaskController` was annotated with `@GetMapping` instead of `@PutMapping`. |
| `404` on task creation | `TaskController` was missing the `/api` prefix on its `@RequestMapping`. |
| `500` on task creation | Empty-body constructor in `Task.java` was being resolved over the correct one due to matching parameter types. Deleted the empty constructor. |
| `Task.created` not-null violation | Constructor parameter order mismatch between `TaskServiceImpl` and `Task`. Fixed argument order to match the correct constructor signature. |
| Inverted null check in `createTask` | `null != task.getTitle()` changed to `null == task.getTitle()`. |
