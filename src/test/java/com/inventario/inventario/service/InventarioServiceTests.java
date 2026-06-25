package com.inventario.inventario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.inventario.inventario.DTO.InventarioDTO;
import com.inventario.inventario.model.Inventario;
import com.inventario.inventario.repository.InventarioRepository;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTests {
    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    private Inventario inventario;
    private InventarioDTO inventarioDTO;

    @BeforeEach
    public void setUp() {
        // Inicializamos la entidad real con datos para que el servicio pueda trabajar
        // sin dar NullPointer
        inventario = new Inventario();
        inventarioDTO = new InventarioDTO();
        inventario.setIdInventario(1L);
        inventario.setIdProducto(1L);
        inventario.setCantidadDisponible(1);
        inventario.setUbicacionBodega("Ubicacion Test");
        inventarioDTO.setIdInventario(1L);
        inventarioDTO.setIdProducto(1L);
        inventarioDTO.setCantidadDisponible(1);
        inventarioDTO.setUbicacionBodega("Ubicacion Test");
    }

    @Test
    public void getAllInventarioTest() {
        when(inventarioRepository.findAll()).thenReturn(List.of(inventario));

        List<InventarioDTO> result = inventarioService.getInventario();

        assertEquals(1, result.size());
        assertEquals(inventario.getIdInventario(), result.get(0).getIdInventario());
    }

    @Test
    void testGetInventarioById() {
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventario));

        InventarioDTO result = inventarioService.getInventarioById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdInventario());
    }

    @Test
    void testGuardarInventario() {
        // Pasamos la entidad al método del servicio tal como lo definiste en el
        // controlador
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

        InventarioDTO result = inventarioService.guardarInventario(inventarioDTO);

        assertNotNull(result);
        assertEquals(inventario.getIdInventario(), result.getIdInventario());
    }

    @Test
    void testActualizarInventario() {
        InventarioDTO updatedEntity = new InventarioDTO();
        updatedEntity.setIdInventario(1L);
        updatedEntity.setIdProducto(1L);
        updatedEntity.setCantidadDisponible(1);
        updatedEntity.setUbicacionBodega("Ubicacion Editada");

        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventario));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(updatedEntity.toModel());

        // Pasamos la entidad editada al servicio
        InventarioDTO result = inventarioService.actualizarInventario(1L, updatedEntity);

        assertNotNull(result);
        assertEquals(updatedEntity.getIdInventario(), result.getIdInventario());
        assertEquals(updatedEntity.getIdProducto(), result.getIdProducto());
        assertEquals(updatedEntity.getCantidadDisponible(), result.getCantidadDisponible());
        assertEquals(updatedEntity.getUbicacionBodega(), result.getUbicacionBodega());
    }

    @Test
    void testEliminarInventario() {
        doNothing().when(inventarioRepository).deleteById(1L);

        inventarioService.eliminarInventario(1L);

        verify(inventarioRepository, times(1)).deleteById(1L);
    }
}
