package com.inventario.inventario.service;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.inventario.DTO.InventarioDTO;
import com.inventario.inventario.model.Inventario;
import com.inventario.inventario.repository.InventarioRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    public List<InventarioDTO> getInventario() {
        log.info("Obteniendo todos los inventarios");
        return inventarioRepository.findAll().stream().map(InventarioDTO::fromModel).collect(Collectors.toList());
    }

    public InventarioDTO getInventarioById(Long idInventario) {
        log.info("Obteniendo inventario con id " + idInventario);
        Optional<Inventario> inventario = inventarioRepository.findById(idInventario);
        return inventario.map(InventarioDTO::fromModel).orElse(null);
    }

    public InventarioDTO guardarInventario(InventarioDTO inventario) {
        log.info("Guardando inventario");
        Inventario inventarioGuardado = inventario.toModel();
        return InventarioDTO.fromModel(inventarioRepository.save(inventarioGuardado));
    }

    public InventarioDTO actualizarInventario(Long idInventario, InventarioDTO inventario) {
        Optional<Inventario> inventarioOptional = inventarioRepository.findById(idInventario);
        if (inventarioOptional.isPresent()) {
            Inventario inventarioActualizado = inventarioOptional.get();
            inventarioActualizado.setIdProducto(inventario.getIdProducto());
            inventarioActualizado.setCantidadDisponible(inventario.getCantidadDisponible());
            inventarioActualizado.setUbicacionBodega(inventario.getUbicacionBodega());
            log.info("Inventario actualizado");
            return InventarioDTO.fromModel(inventarioRepository.save(inventarioActualizado));
        } else {
            log.error("Inventario no encontrado");
            return null;
        }
    }

    public void eliminarInventario(Long idInventario) {
        log.info("Eliminando inventario");
        inventarioRepository.deleteById(idInventario);
    }
}