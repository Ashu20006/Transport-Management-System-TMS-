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

# 🗄️ **Database Schema Diagram**

![ERD Diagram](https://github.com/user-attachments/assets/e9fc40ae-05df-4f19-b340-1f21caaca972)

![ERD Diagram](https://github.com/user-attachments/assets/1cbab974-e6bd-4316-ab11-9e3a6f45ac11")

![ERD Diagram](https://github.com/user-attachments/assets/8c1da53f-eb89-459f-8d72-4197a01252fa)


# 📘 **API Documentation**

![ERD Diagram](https://github.com/user-attachments/assets/c5ebce5a-e2bf-47c4-b8da-684daf977b15)

![ERD Diagram](https://github.com/user-attachments/assets/c8e1c3f3-ecaa-4b5b-924f-e0a1d868cb06)



### Main Endpoints:

#### **Load APIs**
| Method | URL | Description |
|--------|-----|-------------|
| POST | /load | Create load |
| GET | /load | List loads |
| GET | /load/{id} | Get load |
| PATCH | /load/{id}/cancel | Cancel load |
| GET | /load/{id}/active-bids | Get bids for load |

#### **Transporter APIs**
| Method | URL | Description |
|--------|-----|-------------|
| POST | /transporter | Create transporter |
| GET | /transporter/{id} | Get transporter |
| PUT | /transporter/{id}/trucks | Update truck capacity |

#### **Bid APIs**
| Method | URL | Description |
|--------|-----|-------------|
| POST | /bid | Create bid |
| PATCH | /bid/{id}/reject | Reject bid |
| GET | /load/{id}/active-bids | List bids for load |

#### **Booking APIs**
| Method | URL | Description |
|--------|-----|-------------|
| POST | /booking?bidId= | Accept bid (create booking) |
| GET | /booking/{id} | Get booking |
| PATCH | /booking/{id}/cancel | Cancel booking |

---



# 👨‍💻 **How to Run Locally**

### 1️⃣ Clone the repo  
git clone <your-repo-url>


### 2️⃣ Create PostgreSQL database  

CREATE DATABASE tmsdb;

3️⃣ Set DB credentials in application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/tmsdb
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


4️⃣ Run
mvn spring-boot:run


Server →

http://localhost:8080

