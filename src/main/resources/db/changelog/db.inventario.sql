--liquibase formatted sql

--changeset elias:13 
create table inventario (
    id_inventario BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT NOT NULL,
    cantidad_disponible INT NOT NULL,
    ubicacion_bodega VARCHAR(100) NOT NULL
);

--changeset elias:14 
insert into inventario (cantidad_disponible, id_producto, ubicacion_bodega) values
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