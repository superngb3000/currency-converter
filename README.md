# Currency Converter

Клиент-серверное приложение для конвертации валют по данным ЦБ РФ.  
Проект включает **Java 21 + Spring Boot**, **React + TypeScript**, **PostgreSQL**, **JWT-авторизацию** и полностью готов к запуску в **Docker Compose**.

## Структура проекта

```
/
│── currency-converter-back/        # Backend (Spring Boot)
│   ├── src/
|   │── .env  
│   ├── Dockerfile
│   └── ...
│
│── currency-converter-front/       # Frontend (React + TypeScript)
│   ├── src/
|   │── .env  
│   ├── Dockerfile
│   └── ...
│
│── docker-compose.yml              # Запуск всей системы
│── .env                            # Глобальные переменные среды
└── README.md
```

## Запуск через Docker Compose

### 1. Настроить переменные среды (.env в корне)

```env
SERVER_PORT=8080

DB_HOST=db
DB_PORT=5432
DB_NAME=currency_db
DB_USER=postgres
DB_PASSWORD=admin

CB_URL=https://www.cbr-xml-daily.ru/daily_json.js

JWT_SECRET=xN9T6uA1vB3rF8yQ0pS2dW4hE6jK9mZ3xC5vB7nM1qR8tY2uP4sA6wE8dG0zF2
JWT_EXPIRATION_MINUTES=60

ADMIN_USERNAME=admin
ADMIN_PASSWORD=admin

FRONTEND_URL=http://localhost:5173

VITE_API_BASE=http://localhost:8080/api
FRONTEND_PORT=5173
```

### 2. Запуск

```
docker compose up --build
```

## Локальный запуск

### Postgres

Создать экземпляр базы данных

### Backend

#### 1. Настроить переменные среды (../currency-converter-back/.env)

```env
SERVER_PORT=8080

DB_HOST=db
DB_PORT=5432
DB_NAME=currency_db
DB_USER=postgres
DB_PASSWORD=admin

CB_URL=https://www.cbr-xml-daily.ru/daily_json.js

JWT_SECRET=xN9T6uA1vB3rF8yQ0pS2dW4hE6jK9mZ3xC5vB7nM1qR8tY2uP4sA6wE8dG0zF2
JWT_EXPIRATION_MINUTES=60

ADMIN_USERNAME=admin
ADMIN_PASSWORD=admin

FRONTEND_URL=http://localhost:5173

VITE_API_BASE=http://localhost:8080/api
FRONTEND_PORT=5173
```

#### 2. Запуск

```bash
cd currency-converter-back
./gradlew bootRun
```

### Frontend

#### 1. Настроить переменные среды (../currency-converter-front/.env)

```env
VITE_API_BASE=http://localhost:8080/api
```

#### 2. Запуск

```bash
cd currency-converter-front
npm install
npm run dev
```

## Основные API

| Метод | Путь | Описание |
|-------|------|----------|
| POST | /api/auth/login | Авторизация |
| POST | /api/auth/register | Регистрация |
| GET | /api/converter/currencies | Все валюты |
| POST | /api/converter/convert | Конвертация |

## JWT авторизация

Авторизация возвращает:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer"
}
```

Использование:

```
Authorization: Bearer <token>
```

## Swagger

http://localhost:8080/swagger-ui/index.html