# Tasks App

A full-stack task management app. Create task lists, add tasks with priorities and due dates, and track progress — all in a clean dark UI.

---

## Tech Stack

- **Backend** — Java 21, Spring Boot, PostgreSQL
- **Frontend** — React, TypeScript, Vite, NextUI, Tailwind CSS

---

## Prerequisites

- Java 21
- Node.js 22+
- Docker Desktop

---

## Getting Started

### 1. Start the database

```bash
docker compose up -d
```

> ⚠️ If you have PostgreSQL installed locally, stop it first — it'll conflict with Docker on port 5432.

### 2. Run the backend

```bash
./mvnw spring-boot:run
```

Runs on **http://localhost:8080**

### 3. Run the frontend

```bash
cd tasks-fe
npm install
npm run dev
```

Runs on **http://localhost:5173**

---

## API Overview

All endpoints are prefixed with `/api`.

| Resource   | Endpoints                                             |
|------------|-------------------------------------------------------|
| Task Lists | `GET/POST /api/task-lists`                            |
|            | `GET/PUT/DELETE /api/task-lists/{id}`                 |
| Tasks      | `GET/POST /api/task-lists/{listId}/tasks`             |
|            | `GET/PUT/DELETE /api/task-lists/{listId}/tasks/{id}`  |

**Priority values:** `HIGH`, `MEDIUM`, `LOW`  
**Status values:** `OPEN`, `CLOSED`

---

## Configuration

Database credentials are in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tasks
spring.datasource.username=postgres
spring.datasource.password=changemeinprod!
```

> Change the password before deploying anywhere.

 ## Contact

Built with ❤ by [Aradhya Gupta](https://github.com/aradhyagupta25/)

***
