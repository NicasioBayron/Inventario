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
    @Column(name = "idInventario")
    private Long idInventario;
    @Column(name = "idProducto")
    private Long idProducto;
    @Column(name = "cantidadDisponible")
    private Integer cantidadDisponible;
    @Column(name = "ubicacionBodega")
    private String ubicacionBodega;

    public void decreaseStock(Integer quantity) {
        if (this.cantidadDisponible >= quantity) {
            this.cantidadDisponible -= quantity;
        } else {
            throw new RuntimeException("Stock insuficiente");
        }
    }
}
