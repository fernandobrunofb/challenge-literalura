# LiterAlura - Catálogo de Livros

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18.1-blue)
![Status](https://img.shields.io/badge/Status-Concluído-success)

## Sobre o Projeto

**LiterAlura** é uma aplicação de catálogo de livros desenvolvida em Java com Spring Boot. O projeto permite buscar livros através da API Gutendex, armazená-los em um banco de dados PostgreSQL e realizar diversas consultas sobre livros e autores.

Este projeto foi desenvolvido como parte do desafio da **Alura** no programa **Oracle Next Education (ONE)**.

---

## Funcionalidades

O sistema oferece as seguintes funcionalidades através de um menu interativo:

1. ** Buscar livro por título**
   - Busca primeiro no banco de dados local
   - Se não encontrar, busca na API Gutendex
   - Filtra resultados por palavras completas
   - Salva automaticamente no banco de dados

2. ** Listar livros registrados**
   - Exibe todos os livros salvos no banco
   - Mostra título, autor, idioma e número de downloads

3. ** Listar autores registrados**
   - Lista todos os autores cadastrados
   - Exibe nome, ano de nascimento e falecimento

4. ** Listar autores vivos em determinado ano**
   - Busca autores que estavam vivos em um ano específico
   - Utiliza consultas personalizadas no banco

5. ** Listar livros por idioma**
   - Mostra idiomas disponíveis no banco
   - Filtra livros por idioma escolhido

6. ** Estatísticas de livros por idioma**
   - Exibe quantidade de livros por idioma
   - Mostra total geral de livros

---

## Tecnologias Utilizadas

### Backend
- **Java 17** - Linguagem de programação
- **Spring Boot 4.0.1** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Hibernate** - ORM (Object-Relational Mapping)

### Banco de Dados
- **PostgreSQL** - Banco de dados relacional

### Bibliotecas
- **Jackson** - Processamento de JSON
- **HttpClient** - Consumo de API REST

### API Externa
- **API Gutendex** - Base de dados de livros do Projeto Gutenberg

---

## Pré-requisitos

Antes de começar, você precisa ter instalado:

- [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou superior
- [PostgreSQL](https://www.postgresql.org/download/) 
- [Maven](https://maven.apache.org/download.cgi)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) ou outra IDE Java

---

## Como Executar o Projeto

### 1️ - Clone o repositório
```bash
git clone https://github.com/seu-usuario/literalura.git
cd literalura
```

### 2️ - Configure o Banco de Dados

Abra o PostgreSQL e crie o banco de dados:
```sql
CREATE DATABASE literalura_db;
```

### 3️ - Configure o arquivo `application.properties`

Crie ou edite o arquivo `src/main/resources/application.properties`:

```properties
# Configurações do PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA_AQUI
spring.datasource.driver-class-name=org.postgresql.Driver

# Configurações do Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

⚠️ **IMPORTANTE:** Substitua `SUA_SENHA_AQUI` pela senha do seu PostgreSQL!

### 4️ - Execute o projeto

**Opção A - Pelo IntelliJ:**
1. Abra o projeto no IntelliJ
2. Aguarde o Maven baixar as dependências
3. Localize a classe `LiteraluraApplication.java`
4. Clique com botão direito → Run

### 5 - Use o sistema!

O menu aparecerá no console:

```
╔═══════════════════════════════════╗
║        LITERALURA - MENU          ║
╚═══════════════════════════════════╝

1 - Buscar livro por título
2 - Listar livros registrados
3 - Listar autores registrados
4 - Listar autores vivos em determinado ano
5 - Listar livros por idioma
6 - Estatísticas de livros por idioma
0 - Sair

Escolha uma opção:
```

---

## Autor

Desenvolvido por **Fernando Bruno** como parte do desafio LiterAlura da **Alura** em parceria com a **Oracle**.

---

## Licença

Este projeto foi desenvolvido para fins educacionais.

---

## Agradecimentos

- [Alura](https://www.alura.com.br/) - Pelo desafio e aprendizado
- [Oracle Next Education](https://www.oracle.com/br/education/oracle-next-education/) - Pelo programa de formação
- [API Gutendex](https://gutendex.com/) - Pela API de livros gratuita
- [Project Gutenberg](https://www.gutenberg.org/) - Pela base de dados de livros

---

## Contato

- LinkedIn: [fernando-bruno-lima](https://www.linkedin.com/in/fernando-bruno-lima/)
- GitHub: [fernandobrunofb](https://github.com/fernandobrunofb)
- Email: fernandobrunofbsl@gmail.com

---
