CREATE DATABASE IF NOT EXISTS adopcion_mascotas_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE adopcion_mascotas_db;

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_usuarios_username UNIQUE (username),
    CONSTRAINT chk_usuarios_rol CHECK (rol IN ('ADMIN', 'GESTOR', 'OPERADOR'))
);

CREATE TABLE IF NOT EXISTS mascotas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raza VARCHAR(50) NOT NULL,
    edad INT NOT NULL,
    genero VARCHAR(20) NOT NULL,
    ubicacion VARCHAR(100) NOT NULL,
    estado_adopcion VARCHAR(30) NOT NULL,
    foto_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT chk_mascotas_edad CHECK (edad >= 0)
);