# 📚 LiterAlura: Seu Catálogo de Livros

O LiterAlura é um projeto Java com Spring Boot, desenvolvido como parte de um desafio do Programa ONE da Oracle. Ele funciona como um **catálogo de livros e autores** que busca informações da **API gratuita Gutendex**.

---

## 🚀 Como Funciona

O aplicativo permite que você:

* **Busque livros pelo título** na API Gutendex e salve os dados (livro e autor) no seu banco de dados.
* **Liste todos os livros** e autores que já estão salvos no seu banco de dados local.
* **Encontre autores** que estavam vivos em um ano específico.
* **Filtre livros** por idioma.
* **Listar os livros** mais baixados.

---

## ✨ O Que Foi Aprendido/Usado

Este projeto foi uma ótima oportunidade para praticar:

* **Consumo de APIs externas:** Conectar-se e pegar dados de uma API JSON.
* **Persistência de dados:** Salvar e gerenciar informações em um banco de dados **PostgreSQL** usando **Spring Data JPA**.
* **Consultas avançadas (JPQL e Derived Queries):** Criar buscas personalizadas no banco de dados, como:
    * Filtrar por nome ou idioma.
    * Contar e agregar dados (ex: livros mais baixados).
    * Ordenar resultados.
    * Trabalhar com relacionamentos entre tabelas (livros e autores).

**O LiterAlura é um projeto prático que demonstra como integrar APIs e gerenciar dados em uma aplicação Spring Boot.**

---

## 🔩 Tecnologias Utilizadas

* Linguagem: Java
* Framework: Spring Boot
* Gerenciador de Projetos: Maven
* Banco de Dados: PostgreSQL
* Persistência de Dados: Spring Data JPA (com Hibernate)
* API Externa: Gutendex (API gratuita com dados de mais de 70 mil livros)
* Ambiente de Desenvolvimento: IntelliJ IDEA
  
