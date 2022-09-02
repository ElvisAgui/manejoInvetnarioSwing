CREATE DATABASE Tienda_Mariposa;

USE Tienda_Mariposa;

CREATE TABLE usuario (nombre VARCHAR(30) NOT NULL, password VARCHAR (60) NOT NULL, rol TINYINT NOT NULL, CONSTRAINT PK_usuario PRIMARY KEY (nombre));

CREATE TABLE producto (nombre VARCHAR(30) NOT NULL, precio DECIMAL(8,2) NOT NULL, descripcion VARCHAR(120), cantidad_existe INT NOT NULL, CONSTRAINT PK_producto PRIMARY KEY (nombre));

CREATE TABLE categoria (nombre VARCHAR(30) NOT NULL, descripcion VARCHAR(120), CONSTRAINT PK_categoria PRIMARY KEY (nombre));

CREATE TABLE categoria_producto (id INT auto_increment, nombre_Producto VARCHAR(30) NOT NULL, nombre_categoria VARCHAR(30) NOT NULL, CONSTRAINT PK_cateProduc PRIMARY KEY (id), CONSTRAINT FK_nombre_producto FOREIGN KEY (nombre_producto) REFERENCES producto(nombre) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT FK_nombre_categoria FOREIGN KEY (nombre_categoria) REFERENCES categoria(nombre) ON DELETE CASCADE ON UPDATE CASCADE);


INSERT INTO usuario (password, nombre, rol) VALUES ('KfJU71GXYtaoLOPcwGJcFQ==', 'Elvis', '1');
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 token