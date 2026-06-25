package com.inventario.inventario.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario.inventario.model.Inventario;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventarioDTO {
    @JsonIgnore
    private Long idInventario;

    @NotBlank(message = "El id del producto es obligatorio")
    private Long idProducto;

    @NotNull(message = "La cantidad disponible es obligatoria")
    @Min(value = 0, message = "La cantidad disponible debe ser mayor o igual a 0")
    private Integer cantidadDisponible;

    @NotBlank(message = "La ubicacion de la bodega es obligatoria")
    private String ubicacionBodega;

    public Inventario toModel() {
        Inventario inventario = new Inventario();
        inventario.setIdInventario(idInventario);
        inventario.setIdProducto(idProducto);
        inventario.setCantidadDisponible(cantidadDisponible);
        inventario.setUbicacionBodega(ubicacionBodega);
        return inventario;
    }

    public static InventarioDTO fromModel(Inventario inventario) {
        InventarioDTO inventarioDTO = new InventarioDTO();
        inventarioDTO.setIdInventario(inventario.getIdInventario());
        inventarioDTO.setIdProducto(inventario.getIdProducto());
        inventarioDTO.setCantidadDisponible(inventario.getCantidadDisponible());
        inventarioDTO.setUbicacionBodega(inventario.getUbicacionBodega());
        return inventarioDTO;
    }
}
