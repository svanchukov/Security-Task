Проект Spring security, настроен JWT токен при создании пользователя, есть 3 роли: admin, premium_user и guest. 
Подключение к docker бд: docker run --name users-db -p 5432:5432 -e POSTGRES_DB=users -e POSTGRES_USER=users -e POSTGRES_PASSWORD=users postgres:16

Запросы через postman: 
  1. Регистрация: POST  http://localhost:8080/auth/register
      {
          "login": "testUser3",
          "password": "password123",
          "email": "testuser@example3.com",
          "roles": ["ADMIN"]
      }
  2. Залогиниться: POST http://localhost:8080/auth/login
         {
            "login": "testUser3",
            "password": "password123"
         }
  3. Получение по id: GET http://localhost:8080/auth/{id}

