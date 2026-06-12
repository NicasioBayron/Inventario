package com.inventario.inventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;
    @Column(name = "id_producto")
    private Long idProducto;
    @Column(name = "cantidad_disponible")
    private Integer cantidadDisponible;
    @Column(name = "ubicacion_bodega")
    private String ubicacionBodega;

    public void decreaseStock(Integer quantity) {
        if (this.cantidadDisponible >= quantity) {
            this.cantidadDisponible -= quantity;
        } else {
            throw new RuntimeException("Stock insuficiente");
        }
    }
}
