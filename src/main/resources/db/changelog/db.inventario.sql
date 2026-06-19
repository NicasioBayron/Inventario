--liquibase formatted sql

--changeset elias:13 
create table inventario (
    idInventario BIGINT AUTO_INCREMENT PRIMARY KEY,
    idProducto BIGINT NOT NULL,
    cantidadDisponible INT NOT NULL,
    ubicacionBodega VARCHAR(100) NOT NULL
);

--changeset elias:14 
insert into inventario (cantidadDisponible, idProducto, ubicacionBodega) values
(2, 1, 'Bodega A'),
(1, 2, 'Bodega B'),
(4, 3, 'Bodega C'),
(1, 4, 'Bodega D'),
(3, 5, 'Bodega E'),
(2, 6, 'Bodega F'),
(1, 7, 'Bodega G'),
(5, 8, 'Bodega H'),
(2, 9, 'Bodega I'),
(1, 10, 'Bodega J');