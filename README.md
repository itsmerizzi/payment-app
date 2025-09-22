# 💳 Java Payment App

A payment application developed in **Java 25** with **Spring Boot**, using the **Efí Bank API** to create and manage PIX charges.  
The project implements authentication with **Spring Security + JWT**, email verification with **Java Mail Sender + Thymeleaf**, and data persistence with **MySQL**.

---

## 🚀 Tech Stack

- **Java 25**  
- **Spring Boot**  
- **Spring Security + JWT**  
- **Spring Web**  
- **Spring Data JPA**  
- **MySQL**  
- **Docker**  
- **Maven**  
- **Lombok**  
- **Thymeleaf**  
- **Java Mail Sender**  

---

## ⚙️ Features

### 🔐 User API
- User registration:
  - Stores user in the database with `enable = false`.
  - Sends a verification email containing a **unique code** (via Java Mail Sender + Thymeleaf).  
- Verification:
  - User provides the code received by email.
  - If valid → `enable = true`.

### 🔑 Login API
- Authenticates users using `email` and `password`.  
- Issues a **JWT token** on successful authentication.  
- The token must be provided in requests to access protected endpoints.  
- **Spring Security** ensures only verified and authenticated users can access restricted routes.

### 💸 Payment API (PIX)
- Integrates with **Efí Bank API** to handle PIX transactions.  
- Features include:
  - Creation of EVP keys (virtual payment addresses).  
  - Generation of PIX charges (instant payment requests).  
- All PIX operations are securely authenticated and linked to the user system.  

---

## 🐳 How to Run the Project

1. **Clone the repository**  
   ```bash
   git clone https://github.com/your-username/java-payment-app.git
   cd java-payment-app
   ```

2. **Start the MySQL container with Docker**  
   ```bash
   docker run --name mysql-payment -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=paymentdb -p 3306:3306 -d mysql:8
   ```

3. **Configure `application.properties`** with your database credentials.  
   Example:
   ```properties
    spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
    spring.datasource.url=jdbc:mysql://localhost:3306/payments?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    spring.datasource.username=adminuser
    spring.datasource.password=password123
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.hibernate.show-sql=false
    
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=${MAIL_SENDER_USERNAME}
    spring.mail.password=${MAIL_SENDER_PASSWORD}
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    
    jwt.secret=${JWT_SECRET}
    
    server.port=8080
   ```

4. **Build and run the project**  
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 🔒 Security

- JWT is validated on every request.  
- **Spring Security** filters are configured to restrict endpoint access.  
- Users can only access APIs after confirming their email.  

---

## 📧 Registration and Login Flow

1. User registers → receives an email with a code.  
2. User validates the code → account is enabled.  
3. User logs in → receives JWT token.  
4. User uses the token to access protected endpoints (e.g., PIX charge creation).  

