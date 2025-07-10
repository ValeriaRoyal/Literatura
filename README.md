# Sistema de Gerenciamento de Literatura

## Descrição

Sistema para gerenciamento de autores e livros, desenvolvido com Spring Boot e PostgreSQL.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**

## Funcionalidades

### Autores
- Cadastro completo de autores (nome, data de nascimento/morte, nacionalidade, biografia)
- Busca por nome, nacionalidade
- Listagem de autores vivos
- CRUD completo

### Livros
- Cadastro completo de livros (título, data de publicação, gênero, sinopse, ISBN, etc.)
- Relacionamento com autores
- Busca por título, gênero, autor, editora
- Filtros por período de publicação
- CRUD completo

## Configuração do Banco de Dados

### Para Desenvolvimento (H2 Database)
O projeto está configurado para usar H2 Database em memória para desenvolvimento, que não requer instalação adicional.

Para executar em modo de desenvolvimento:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Acesse o console H2 em: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:literatura_db`
- Username: `sa`
- Password: (deixe em branco)

### Para Produção (PostgreSQL)
1. Instale o PostgreSQL
2. Crie um banco de dados chamado `literatura_db`
3. Configure as credenciais no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literatura_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## Como Executar

### Desenvolvimento
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Produção
1. Configure o banco PostgreSQL
2. Execute o comando:

```bash
mvn spring-boot:run
```

### Testes
```bash
mvn test
```

## Endpoints da API

### Autores

- `GET /api/autores` - Listar todos os autores
- `GET /api/autores/{id}` - Buscar autor por ID
- `GET /api/autores/buscar?nome={nome}` - Buscar autores por nome
- `GET /api/autores/nacionalidade/{nacionalidade}` - Buscar por nacionalidade
- `GET /api/autores/vivos` - Listar autores vivos
- `POST /api/autores` - Criar novo autor
- `PUT /api/autores/{id}` - Atualizar autor
- `DELETE /api/autores/{id}` - Deletar autor

### Livros

- `GET /api/livros` - Listar todos os livros
- `GET /api/livros/{id}` - Buscar livro por ID
- `GET /api/livros/buscar?titulo={titulo}` - Buscar livros por título
- `GET /api/livros/genero/{genero}` - Buscar por gênero
- `GET /api/livros/autor/{autorId}` - Buscar livros de um autor
- `GET /api/livros/autor/nome/{nomeAutor}` - Buscar por nome do autor
- `GET /api/livros/editora/{editora}` - Buscar por editora
- `GET /api/livros/periodo?dataInicio={data}&dataFim={data}` - Buscar por período
- `POST /api/livros` - Criar novo livro
- `PUT /api/livros/{id}` - Atualizar livro
- `DELETE /api/livros/{id}` - Deletar livro

## Exemplos de JSON

### Criar Autor:
```json
{
    "nome": "Machado de Assis",
    "dataNascimento": "1839-06-21",
    "dataMorte": "1908-09-29",
    "nacionalidade": "Brasileiro",
    "biografia": "Joaquim Maria Machado de Assis foi um escritor brasileiro..."
}
```

### Criar Livro:
```json
{
    "titulo": "Dom Casmurro",
    "dataPublicacao": "1899-01-01",
    "genero": "Romance",
    "sinopse": "Romance narrado em primeira pessoa...",
    "isbn": "978-85-359-0277-5",
    "numeroPaginas": 256,
    "editora": "Ática",
    "idioma": "Português",
    "autor": {
        "id": 1
    }
}
```

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/exemplo/literatura/
│   │   ├── controller/
│   │   │   ├── AutorController.java
│   │   │   └── LivroController.java
│   │   ├── model/
│   │   │   ├── Autor.java
│   │   │   └── Livro.java
│   │   ├── repository/
│   │   │   ├── AutorRepository.java
│   │   │   └── LivroRepository.java
│   │   └── LiteraturaApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/exemplo/literatura/
        └── LiteraturaApplicationTests.java
```
