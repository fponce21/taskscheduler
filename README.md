# 🕒 Distributed Task Scheduler

This project is a distributed task scheduling system built with **Spring Boot**, **RabbitMQ**, and **PostgreSQL**. It allows clients to create tasks via a REST API, which are then processed asynchronously using a queue-based architecture. The application logs everything using **Log4j2** and is ready to run via **Docker Compose** or your local development environment.

---

## 📦 What This Project Does

- Accepts new tasks via a REST API
- Publishes tasks to a RabbitMQ queue
- Consumes tasks and marks them as complete or failed
- Stores task data and status in PostgreSQL
- Logs each stage of the task lifecycle using Log4j2
- Runs securely and reproducibly using Docker

---

## 🧰 Tech Stack

- Java 17 + Spring Boot 3
- PostgreSQL (task database)
- RabbitMQ (queue for distributing tasks)
- Log4j2 (logging to file + console)
- Docker + Docker Compose
- JUnit 5 + Mockito (unit testing)

---

## 🚀 How to Run the App

This app can be run two ways:

---

### 🅰️ OPTION A — Run Entire Stack with Docker Compose

This runs **PostgreSQL**, **RabbitMQ**, and your **Spring Boot app** inside Docker.

#### 🛠️ Step-by-Step:

```bash
# 1. Clone the repo
git clone https://github.com/your-username/distributed-task-scheduler.git
cd distributed-task-scheduler

# 2. Build the Spring Boot app
./mvnw clean package -DskipTests

# 3. Build Docker image for your app
docker build -t task-scheduler-app .

# 4. Start everything using Docker Compose
docker-compose up

---

### OPTION B — Run App Locally (with Docker for Dependencies Only)

If you want to run Spring Boot in your IDE (e.g. Eclipse/IntelliJ) and only use Docker for RabbitMQ/Postgres:

#### 🛠️ Step-by-Step:

# 1. Start only RabbitMQ + PostgreSQL via Compose
docker-compose up rabbitmq postgres

# 2. In your application.yml, ensure it points to:
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/taskscheduler
    username: postgres
    password: postgres
  rabbitmq:
    host: localhost
    username: task_user
    password: strong_password

# 3. Run the Spring Boot app locally (from IDE or CLI)
./mvnw spring-boot:run

---

API Endpoints:

| METHOD | PATH              | USAGE             |
| ------ | ----------------- | ----------------- |
| POST   | `/api/tasks`      | Create a new task |
| GET    | `/api/tasks`      | Get all tasks     |
| GET    | `/api/tasks/{id}` | Get task by ID    |

---

Running Tests:
./mvnw test

---

Tests cover:

Task creation logic (TaskService)
Task lookup by ID
Message publishing

---

🛡️ Security Highlights:

RabbitMQ credentials are secured (task_user with strong password)
Tasks are validated using DTOs and @Valid
Consumers are idempotent — they skip already-completed tasks
Logs are sanitized and structured

---

🚧 Future Improvements:

Retry failed tasks using DLQ
JWT authentication on REST API
Web dashboard to view tasks (Angular/React)
Task prioritization or delays

---

👤 Author
Francisco Ponce
Full-Stack Software Engineer




