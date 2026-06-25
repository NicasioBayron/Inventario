package com.inventario.inventario.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.inventario.inventario.DTO.InventarioDTO;
import com.inventario.inventario.controller.InventarioController;

@Component
public class InventarioAssembler implements RepresentationModelAssembler<InventarioDTO, EntityModel<InventarioDTO>> {
    @Override
    public EntityModel<InventarioDTO> toModel(InventarioDTO inventarioDTO) {
        return EntityModel.of(inventarioDTO,
                linkTo(methodOn(InventarioController.class)
                        .getInventarioById(inventarioDTO.getIdInventario()))
                        .withSelfRel().withType("GET"),
                linkTo(methodOn(InventarioController.class).actualizarInventario(
                        inventarioDTO.getIdInventario(),
                        inventarioDTO)).withRel("Actualizar Inventario").withType("PUT"),
                linkTo(methodOn(InventarioController.class)
                        .eliminarInventario(inventarioDTO.getIdInventario()))
                        .withRel("Eliminar Inventario").withType("DELETE"));
    }
}
