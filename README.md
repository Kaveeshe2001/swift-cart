# 🛒 SwiftCart – Full Stack Project Base Guide

SwiftCart is a **full-stack e-commerce platform project base** designed as a monorepo. It includes a **Spring Boot backend** and a **React (Vite) frontend**, providing a clean, scalable foundation for team-based development.

---

## 🛠 Tech Stack

| Layer        | Technology                               |
| ------------ | ---------------------------------------- |
| **Backend**  | Java 21, Spring Boot 3.x, Maven, JPA     |
| **Frontend** | React (Vite), Tailwind CSS v4, Shadcn UI |
| **Database** | MySQL 8.0+                               |

---

## 📂 What is a Project Base?

A **Project Base** (boilerplate) is the initial skeleton of the application.

As the **Project Leader**, setting this up ensures:

- **Consistency** – Same folder structure for all developers
- **Efficiency** – Common configs (DB, CORS) are pre-configured
- **Standards** – Enforces clean 3-tier architecture

```
Controller → Service → Repository
```

---

## 🚀 Getting Started

### 1️⃣ Database Configuration

1. Create a MySQL database:

```sql
CREATE DATABASE swift_cart;
```

2. Update credentials in:

```
backend/src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/swiftcart_db
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 2️⃣ Backend Setup (Spring Boot)

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

#### ✅ Verification

Open:

```
http://localhost:8080/health
```

Expected response:

```json
{
  "status": "UP",
  "message": "SwiftCart Backend is Live"
}
```

---

### 3️⃣ Frontend Setup (React + Vite)

```bash
cd frontend
npm install
npm run dev
```

#### ✅ Verification

Frontend runs at:

```
http://localhost:5173
```

(or the port shown by Vite)

---

## 🎨 Working with Shadcn UI

Shadcn UI is used as the component library.  
Components are added **only when required**.

### ➕ Add a Component

```bash
npx shadcn@latest add [component-name]
```

Example:

```bash
npx shadcn@latest add button
```

---

## 📂 Project Structure

```plaintext
swift-cart/
├── backend/
│   └── src/main/java/com/swiftcart/
│       ├── config/
│       ├── controller/
│       ├── dtos/
│       ├── model/
│       ├── repository/
│       └── service/
│
├── frontend/
│   ├── src/components/
│   ├── src/lib/
│   └── src/assets/
│
└── README.md
```

---

## 🤝 Team Guidelines

- ✅ Always verify the `/health` endpoint before starting development
- 🌱 Create a new branch per feature:

- 🧱 Follow **Controller → Service → Repository** architecture
- ❌ Never access repositories directly from controllers
- 🌐 Backend allows CORS from:

```
http://localhost:5173
```

---

## 🚀 Ready to Build
