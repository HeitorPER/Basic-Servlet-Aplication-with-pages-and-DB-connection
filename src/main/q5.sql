CREATE DATABASE IF NOT EXISTS avaliacao_mysql;
USE avaliacao_mysql;

CREATE TABLE IF NOT EXISTS categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    prioridade VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS categoria_assunto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    categoria_id INT NOT NULL,
    assunto VARCHAR(50) NOT NULL,
    CONSTRAINT fk_categoria_assunto
        FOREIGN KEY (categoria_id) REFERENCES categoria(id)
        ON DELETE CASCADE
);
