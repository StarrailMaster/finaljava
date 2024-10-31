# Intelligent-Ecommerce-Application

**Intelligent E-commerce for ELEC 5620**

---

### Deployment Instructions
The deployment process packages the project files using Docker, builds the project using Jenkins, and finally deploys it to Alibaba Cloud servers.

---

### Advanced Technologies
1. **Application Frameworks**:
   - React
   - Next.js
2. **Cloud Services**:
   - Firebase
   - Aliyun Cloud
3. **Deployment Systems**:
   - Docker
   - Jenkins

---

### Dependencies

- **Frontend**:
  - `axios`: ^1.7.7
  - `next`: 14.2.15
  - `react`: ^18
  - `react-dom`: ^18

- **Backend**:
  - `spring-boot-starter-data-redis-reactive`: 3.3.2
  - `spring-boot-starter-web`: 3.3.2
  - `spring-boot-starter-security`: 3.3.2
  - `firebase-admin`: 8.1.0
  - `okhttp`: 4.10.0
  - `java-dotenv`: 5.2.2
  - `json`: 20210307

- **Test Dependencies**:
  - `spring-boot-starter-test`: 3.3.2
  - `reactor-test`: 3.3.2

---

### Adding a `.env` File

To manage sensitive information and configurations, please follow the steps below to add a `.env` file:

1. **Create a `.env` File**  
   In the root directory of the project, create a file named `.env`. This file will be used to store environment variables such as API keys and database connection configurations.

2. **Set Environment Variables**  
   Define the required environment variables in the `.env` file. Each variable should be on a new line, following this format:

   ```plaintext
   OPENAI_API_KEY=sk-your-api-key
