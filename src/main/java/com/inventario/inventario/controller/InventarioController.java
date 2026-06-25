package com.inventario.inventario.controller;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventario.inventario.DTO.InventarioDTO;
import com.inventario.inventario.assembler.InventarioAssembler;
import com.inventario.inventario.model.Inventario;
import com.inventario.inventario.service.InventarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/inventario")
public class InventarioController {
    private final InventarioService inventarioService;
    private final InventarioAssembler inventarioAssembler;

    public InventarioController(InventarioService inventarioService, InventarioAssembler inventarioAssembler) {
        this.inventarioService = inventarioService;
        this.inventarioAssembler = inventarioAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<InventarioDTO>> getInventario() {
        log.info("Obteniendo todos los inventarios");
        List<EntityModel<InventarioDTO>> inventarios = inventarioService.getInventario().stream()
                .map(inventarioAssembler::toModel)
                .collect(Collectors.toList());
        CollectionModel<EntityModel<InventarioDTO>> modelo = CollectionModel.of(inventarios,
                linkTo(methodOn(InventarioController.class).guardarInventario(null)).withRel("Guardar Inventario")
                        .withType("POST"));
        return modelo;
    }

    @GetMapping("/{idInventario}")
    public EntityModel<InventarioDTO> getInventarioById(@PathVariable Long idInventario) {
        log.info("OBTENIENDO INVENTARIO CON ID: " + idInventario);
        InventarioDTO inventario = inventarioService.getInventarioById(idInventario);
        EntityModel<InventarioDTO> modelo = inventarioAssembler.toModel(inventario);
        modelo.add(linkTo(methodOn(InventarioController.class).getInventario())
                .withRel("Obtener todos los inventarios"));
        return modelo;
    }

    @PostMapping
    public ResponseEntity<InventarioDTO> guardarInventario(@RequestBody InventarioDTO inventarioDTO) {
        log.info("Creando inventario");
        InventarioDTO inventario = inventarioService.guardarInventario(inventarioDTO);
        return ResponseEntity.ok(inventario);
    }

    @PutMapping("/{idInventario}")
    public ResponseEntity<InventarioDTO> actualizarInventario(@PathVariable Long idInventario,
            @RequestBody InventarioDTO inventario) {
        log.info("Actualizando inventario");
        InventarioDTO inventarioActualizado = inventarioService.actualizarInventario(idInventario, inventario);
        return ResponseEntity.ok(inventarioActualizado);
    }

    @DeleteMapping("/{idInventario}")
    public ResponseEntity<String> eliminarInventario(@PathVariable Long idInventario) {
        log.info("Eliminando inventario");
        inventarioService.eliminarInventario(idInventario);
        return ResponseEntity.ok("Inventario eliminado exitosamente");
    }
}
