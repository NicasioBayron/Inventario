package com.inventario.inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventario.inventario.model.Inventario;
import com.inventario.inventario.service.InventarioService;

@RestController
@RequestMapping("/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<Inventario>> getInventario() {
        return ResponseEntity.ok(inventarioService.getInventario());
    }

    @GetMapping("/{idInventario}")
    public ResponseEntity<Inventario> getInventario(@PathVariable Long idInventario) {
        Inventario inventario = inventarioService.obtenerInventario(idInventario);
        return inventario != null ? ResponseEntity.ok(inventario) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Inventario> guardarInventario(@RequestBody Inventario inventario) {
        return ResponseEntity.ok(inventarioService.guardarInventario(inventario));
    }

    @PutMapping("/{idInventario}")
    public ResponseEntity<Inventario> actualizarInventario(@PathVariable Long idInventario,
            @RequestBody Inventario inventario) {
        Inventario inventarioActualizado = inventarioService.obtenerInventario(idInventario);
        if (inventarioActualizado != null) {
            inventarioActualizado.setCantidadDisponible(inventario.getCantidadDisponible());
            inventarioActualizado.setUbicacionBodega(inventario.getUbicacionBodega());
            inventarioActualizado.setIdProducto(inventario.getIdProducto());
            return ResponseEntity.ok(inventarioService.actualizarInventario(inventarioActualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idInventario}")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Long idInventario) {
        inventarioService.eliminarInventario(idInventario);
        return ResponseEntity.noContent().build();
    }
}
