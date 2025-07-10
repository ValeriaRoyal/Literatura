# Novo Projeto Spring Boot

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**

## Configuração do Banco de Dados

1. Instale o PostgreSQL
2. Crie um banco de dados
3. Configure as credenciais no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## Como Executar

1. Clone o repositório
2. Configure o banco de dados PostgreSQL
3. Execute o comando:

```bash
mvn spring-boot:run
```

## Endpoints da API

### Usuários

- `GET /api/usuarios` - Listar todos os usuários
- `GET /api/usuarios/{id}` - Buscar usuário por ID
- `POST /api/usuarios` - Criar novo usuário
- `PUT /api/usuarios/{id}` - Atualizar usuário
- `DELETE /api/usuarios/{id}` - Deletar usuário

### Exemplo de JSON para criar usuário:

```json
{
    "nome": "João Silva",
    "email": "joao@email.com"
}
```

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/exemplo/novoprojeto/
│   │       ├── controller/
│   │       ├── model/
│   │       ├── repository/
│   │       └── NovoProjetoApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/exemplo/novoprojeto/
```
