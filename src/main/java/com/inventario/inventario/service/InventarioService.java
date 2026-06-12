package com.inventario.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.inventario.model.Inventario;
import com.inventario.inventario.repository.InventarioRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    public Inventario obtenerInventario(Long idInventario) {
        return inventarioRepository.findById(idInventario).orElse(null);
    }

    public Inventario guardarInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public void eliminarInventario(Long idInventario) {
        inventarioRepository.deleteById(idInventario);
    }

    public Inventario actualizarInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public List<Inventario> getInventario() {
        return inventarioRepository.findAll();
    }

    public Inventario findByIdProducto(Long idProducto) {
        return inventarioRepository.findByIdProducto(idProducto);
    }
}