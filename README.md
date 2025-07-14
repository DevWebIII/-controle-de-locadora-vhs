# Controle de Locadora de VHS

Este projeto é uma aplicação web desenvolvida como trabalho semestral para a disciplina de Desenvolvimento Web III (TADS - IFPR), com o objetivo de gerenciar uma locadora de fitas VHS antigas. O sistema permite o cadastro, edição, exclusão e visualização de fitas, bem como o gerenciamento de categorias e controle de status das fitas.

## Objetivos

- Consolidar os conceitos de desenvolvimento web com **Spring Boot**
- Aplicar o padrão **arquitetural em camadas**
- Utilizar **persistência com ORM (JPA/Hibernate)**
- Implementar **validação de formulários**
- Aplicar boas práticas de **modelagem orientada a objetos**
- Criar **controle de usuários com autenticação**

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
  - Spring Web
  - Spring Data JPA
  - Spring Boot DevTools
- **Thymeleaf**
- **MySQL**
- **Lombok**
- **Maven**
- **HTML/CSS (Bootstrap 5)**

---

## Estrutura do Projeto

```
controle-de-locadora-vhs/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controllers/
│   │   │   ├── entities/
│   │   │   ├── filters/
│   │   │   ├── repositories/
│   │   │   └── services/
│   │   └── resources/
│   │       ├── templates/
│   │       ├── static/
│   │       └── application.properties
├── README.md
└── pom.xml
```
---

## Funcionalidades Implementadas

- ✅ Cadastro de fitas VHS  
- ✅ Listagem e busca de fitas  
- ✅ Edição e exclusão de fitas  
- ✅ Cadastro, edição e exclusão de categorias  
- ✅ Enumeração para o status das fitas:
  - `DISPONIVEL`
  - `EMPRESTADA`
  - `INDISPONIVEL`
- ✅ Validação de formulários  
- ✅ Controle de acesso por login (usuário autenticado)  
- ✅ Arquitetura MVC em camadas  
- ✅ Persistência em banco de dados relacional via ORM  

---


## Observação Importante

Para cadastrar uma fita VHS, é necessário que exista pelo menos uma categoria cadastrada diretamente no banco de dados, pois o sistema depende da categoria para o cadastro da fita. Caso não haja categorias, a aplicação retornará erro 500.

### Exemplo de inserção direta via SQL:
```
INSERT INTO categoria (nome) VALUES ('Ação');
```

## Como Executar o Projeto

### Pré-requisitos

- Java 21+  
- MySQL  
- Maven  

### Passos

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/controle-de-locadora-vhs.git
   cd controle-de-locadora-vhs
