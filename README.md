# Assignment
Create a simple microservice ecosystem that can behave like a very simple transaction system (with accounts and transfers).

### **Features:**

- users can create accounts with simple details (Name; email; opening amount)
- users can get account information.
- users of the api can transfer money.
- optional – authentication.
- optional – persistent storage

### **Constraints:**

- use java 17+
- easy to build (compile)
- timebox to ~ 4hours
- Git commit history


### **Tasks**
1. [x] Git Repo
2. [x] Create Initial application 
3. [x] Create Account Service - Account Entity / Repository / Service / Controller
4. [ ] Create Transaction Service - Transaction Entity / Repository / Service / Controller
5. [ ] Create Test Cases
6. [ ] Create Dockerfile
7. [ ] Create Docker-compose
8. [ ] Create Documentation


### **Run**
```mvn clean install```

```cd target```

```java -jar .\assignment-0.0.1-SNAPSHOT.jar --spring.profiles.active=local```
