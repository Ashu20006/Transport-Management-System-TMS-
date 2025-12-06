# 🚚 Transport Management System (TMS) — Backend Assignment  
**Author:** Ashutosh Kumar  

This project implements a Transport Management System backend using **Spring Boot, Java 17, PostgreSQL**, and **Spring Data JPA**.  
It models real-world logistics operations including **loads, bids, transporters, and bookings** with proper business rules, validations, and concurrency control.

---

# 📘 Table of Contents  
- [Overview](#overview)  
- [Features Implemented](#features-implemented)  
- [Tech Stack](#tech-stack)  
- [Project Architecture](#project-architecture)  
- [Folder Structure](#folder-structure)  
- [Database Schema Diagram](#database-schema-diagram)  
- [API Documentation](#api-documentation)  
- [How to Run Locally](#how-to-run-locally)  
- [Postman Collection](#postman-collection)  
- [Test Coverage Screenshot](#test-coverage-screenshot)  
- [Author](#author)

---

# 📝 **Overview**

The Transport Management System allows:

- Shippers to **post loads**  
- Transporters to **register & offer trucks**  
- Transporters to **bid on loads**  
- System to **allocate bookings** based on business rules  
- Prevent **double booking** using **optimistic locking**  

The key goal is to simulate real-world logistics workflows.

---

# ✨ **Features Implemented**

### ✔ Load Management  
- Create load  
- List loads with filters & pagination  
- Auto status transitions:  
  - `POSTED → OPEN_FOR_BIDS` (first bid)  
  - `OPEN_FOR_BIDS → BOOKED` (required trucks allocated)  
- Cancel load  
- Optimistic locking using `@Version`  

### ✔ Transporter Management  
- Register transporter  
- Add/update truck availability  
- Fetch transporter details  

### ✔ Bid Management  
- Place bid with capacity validation  
- Prevent bidding on CANCELLED or BOOKED loads  
- Reject bid  
- List bids  
- Best bid scoring:
score = (1 / proposedRate) * 0.7 + (rating / 5) * 0.3


### ✔ Booking Management  
- Accept bid → create booking  
- Deduct truck capacity  
- Enforce “one accepted bid per load”  
- Prevent double booking (optimistic locking)  
- Cancel booking → restore trucks  

---

# 🛠️ **Tech Stack**

- **Java 17**  
- **Spring Boot 3.x**  
- **Spring Data JPA**  
- **PostgreSQL**  
- **Maven**  
- **Global Exception Handling (@ControllerAdvice)**  

---

# 🏗️ **Project Architecture**

Controller
↓
Service (Business Logic)
↓
Repository (Database Access)
↓
Entity (Data Model)


All business rules are written inside the **Service layer**, keeping controllers clean.

---

# 📂 **Folder Structure**

src/main/java/com/ashutosh/tms
│
├── controller/
├── service/
├── repository/
├── entity/
│ └── enums/
├── exception/


![ERD Diagram](https://github.com/user-attachments/assets/a39129a5-046c-4864-8029-bdcec81ac995)

