# KK Single Page

Single Page React application demo with Spring Boot (Kotlin) + Postegre backend. Including simple JWT user autentication.

```bash
APP: kjc-int
DIRECTORY STRUCTURE: /opt/kjc/int/kk-single-page
```

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Examples](#examples)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Title**: Description.

## Command usage

Run the script followed by the command you wish to execute:

```bash
<command>
Replace <command> with the specific one-liner command you want to execute.
```

## Installation and usage guide

1. **Go to installation path**:

```bash
cd /opt/kjc/int/kk-single-page/backend
```

2. **Make a build**:

```bash
./gradlew clean build
```

3. **Run the project**:

```bash
./gradlew bootRun
```

4. **Serve frontend**:

```bash
cd /opt/kjc/int/kk-single-page/frontend
npm install
npm start
```

5. **To login on frontend**:

Go to: http://localhost:3000/

```bash
Test accounts:
 - Username: user, password: pass
 - Username: admin, password: admin123
 ```

6. **Using the endpoints on terminal**:

First navigate with console into the project:
```bash
cd /opt/kjc/int/kk-single-page/backend/
```

Request token:
```bash
curl http://localhost:8080/api/public/token -w "\n"
```

Trying to access without token:
```bash
curl http://localhost:8080/api/secure/data -w "\n"
```

Access with token:
```bash
curl -H "Authorization: Bearer YOUR_TOKEN_HERE" http://localhost:8080/api/secure/data -w "\n"
```

List data without filter with auth:
```bash
curl -H "Authorization: Bearer YOUR_TOKEN_HERE" http://localhost:8080/api/data -w "\n"
```

List data with Language-filter with auth:
```bash
curl -H "Authorization: Bearer YOUR_TOKEN_HERE" http://localhost:8080/api/data?category=Language -w "\n"
```

List data sorted by name with auth:
```bash
curl -H "Authorization: Bearer YOUR_TOKEN_HERE" http://localhost:8080/api/data?sortBy=name&sortOrder=desc -w "\n"
```

Search a singular record by id with auth:
```bash
curl -H "Authorization: Bearer YOUR_TOKEN_HERE" http://localhost:8080/api/data/4 -w "\n"
```

Add a record to the list with auth:
```bash
curl -X POST -H "Authorization: Bearer YOUR_TOKEN" -H "Content-Type: application/json" -d '{"id":11,"name":"Javascript","category":"Language"}' http://localhost:8080/api/data -w "\n"
```

Delete a record from the list with auth:
```bash
curl -X DELETE -H "Authorization: Bearer YOUR_TOKEN" http://localhost:8080/api/data/99 -w "\n"
```

Run the Healthcheck endpoint
```bash
curl -v http://localhost:8080/api/healthcheck -w "\n"
```

## Troubleshooting

If port is in use:

Run this in terminal:
```bash
sudo lsof -i :8080
```

Then this to show the port:
```bash
java 12345 youruser 123u IPv6 0x.... TCP *:http (LISTEN)
```

Then destroy it:
```bash
sudo kill -9 12345
```

## Architecture Overview

## General Overview

The system consists of a separate frontend (React) and backend (Spring Boot with Kotlin). The frontend communicates with the backend over a REST API. JWT-based authentication is in use.

## Backend
* Framework: Spring Boot 3.4.4 (Kotlin 1.9.25)
* REST API:
  * GET /api/items – fetches a list
  * GET /api/items/{id} – fetches a single entry
  * POST /api/items – adds a new entry
  * GET /api/healthcheck – health check
* Authentication: JWT-based, token validation handled at middleware level
* No user registration yet; predefined hardcoded users
* Data Storage:
  * Initially in-memory/mock
  * PostgreSQL added later

## Frontend
* Framework: React + TypeScript
* SPA, uses fetch/axios calls to the backend
* Login view and data view
* Not yet; Data can be added and exported in CSV format

## Development Infrastructure
* Backend and frontend are developed as separate projects
* Docker Compose-based runtime environment is a potential future setup

### Folder structure
```bash
/opt/kjc/int/kk-single-page/
├── backend/
│   ├── gradle/wrapper
│   │   ├── gradle-wrapper.jar
│   │   └── gradle-wrapper.properties
│   ├── src/                     
│   │   ├── main/  
│   │   │   ├── kotlin/com/katariinatuulia/com/kk_single_page/ 
│   │   │   │   ├── DataListings.kt
│   │   │   │   ├── HealthController.kt
│   │   │   │   ├── HomeContainer.kt
│   │   │   │   ├── KkSinglePageApplication.kt
│   │   │   │   ├── JwtAuthentication.kt
│   │   │   │   └── UserController.kt
│   │   │   └── resources/
│   │   │       └── application.properties                    
│   │   └── test/kotlin/com/katariinatuulia/com/kk_single_page/
│   │       ├── KkSinglePageApplicationTests.kt
│   │       └── SimpleTest.kt
│   ├── .gitattributes
│   ├── .gitignore
│   ├── build.gradle
│   ├── gradlew
│   ├── gradlew.bat
│   └── settings.gradle
├── frontend/                     
│   ├── public/
│   │   ├── favicon.ico
│   │   ├── index.html
│   │   └── etc..
│   ├── src/
│   │   ├── components/
│   │   │   ├── AuthController.java
│   │   │   ├── Layout.tsx
│   │   │   └── LoginForm.tsx
│   │   ├── images/
│   │   │   └── background-graphic.jpg
│   │   ├── pages/
│   │   │   ├── Dashboard.tsx
│   │   │   └── Home.tsx
│   │   ├── styles/
│   │   │   ├── styles.sass
│   │   │   └── tailwind.css
│   │   ├── App.css
│   │   ├── App.test.tsx
│   │   ├── App.tsx
│   │   └── etc...
│   ├── .gitignore
│   ├── package-lock.json
│   ├── package.json
│   ├── README.md
│   └── tsconfig.json
├── usage-examples/
│   └── multiple screenshots...
└── README.md
```

### Program diagram:
```bash
/opt/kjc/int/kk-single-page/
├── Frontend application
│   └── Login form
├── Authentication
│   ├── Filter
│   └── Tokens
├── Resource Server
│   └── Endpoints
└── Security
    └── Security Config
```

### Version info:

Tested with Ubuntu 20.04, springboot 3.4.4, java 21 and kotlin 1.9.25
