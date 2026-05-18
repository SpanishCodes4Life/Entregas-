![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB) <br> ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

O projeto é uma aplicação de entregas construída com Spring Boot, usando JPA para mapear as entidades do domínio (Clientes, Endereços, Entregas, Transportadoras e Histórico de Status) e H2 como banco em memória para persistência durante o desenvolvimento. O backend expõe APIs REST para criar, listar, atualizar e excluir registros, além de permitir a consulta de CEP via serviço externo, garantindo que os dados de endereço sejam preenchidos de forma integrada e organizada.

No frontend, usamos React com Bootstrap para criar uma interface leve e responsiva, onde o usuário pode cadastrar clientes, consultar transportadoras e criar entregas relacionadas aos clientes existentes. A interface consome as mesmas APIs do Spring Boot, oferecendo uma experiência fluida de CRUD entre frontend e backend sem precisar de mudanças complexas na arquitetura.

## Como rodar:
### 1° Faça um git clone do Repositório
### 2° Rode a aplicação spring.
### 3° Rode o front-end com:
* npm install na pasta frontend
* npm run dev 
### 4° Navegue até localhost:5173
