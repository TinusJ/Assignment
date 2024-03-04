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
4. [X] Create Transaction Service - Transaction Entity / Repository / Service / Controller
5. [X] Create Test Cases
6. [X] Create Dockerfile
7. [X] Create Docker-compose
8. [X] Create Documentation


### **Run**
```mvn clean install```

```cd target```

```java -jar .\assignment-0.0.1-SNAPSHOT.jar --spring.profiles.active=local```


### **Docker**
```docker-compose up -d```

### **API**
##### Postman Collection
[Assignment.postman_collection.json](Assignment.postman_collection.json)

##### Create Account
```
curl --location 'http://localhost:8080/api/v1/accounts/create' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=24EC3ED0959E457C74140C3F4BDEED06' \
--data-raw '{
    "name":"Tinus",
    "email":"Tinus2@gmail.com",
    "balance":12000
}'
```

##### View Account
```
curl --location 'http://localhost:8080/api/v1/accounts/Tinus2@gmail.com' \
--header 'Cookie: JSESSIONID=5E4B63F6A855D06BD7E4F514FA51A34E'
```

##### Transfer 
```
curl --location 'http://localhost:8080/api/v1/transactions/transfer' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=24EC3ED0959E457C74140C3F4BDEED06' \
--data-raw '{
    "sourceAccountEmail":"Tinus2@gmail.com",
    "targetAccountEmail":"Tinus2@gmail.com",
    "amount":13000
}'
```