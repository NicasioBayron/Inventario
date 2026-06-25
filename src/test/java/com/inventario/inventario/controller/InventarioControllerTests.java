package com.inventario.inventario.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.inventario.inventario.DTO.InventarioDTO;
import com.inventario.inventario.assembler.InventarioAssembler;
import com.inventario.inventario.service.InventarioService;

@WebMvcTest(InventarioController.class)
@Import(InventarioAssembler.class)
public class InventarioControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private InventarioService inventarioService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockitoBean
    private InventarioAssembler inventarioAssembler;

    @Test
    public void testListarProvedores() throws Exception {
        // 1. Creamos entidades de prueba reales para simular la salida del servicio
        InventarioDTO p1 = new InventarioDTO();
        p1.setIdInventario(1L);
        p1.setIdProducto(1L);
        p1.setCantidadDisponible(1);
        p1.setUbicacionBodega("Bodega 1");

        List<InventarioDTO> listaEntidades = new ArrayList<>();
        listaEntidades.add(p1);
        // 2. Mockeamos el servicio
        when(inventarioService.getInventario()).thenReturn(listaEntidades);

        // 3. ¡CLAVE! Mockeamos el assembler también para el stream: que devuelva un
        // EntityModel válido y no un null
        when(inventarioAssembler.toModel(any())).thenReturn(org.springframework.hateoas.EntityModel.of(p1));

        // 4. Ejecutamos contra la URL del controlador (/proveedores)
        mockMvc.perform(get("/inventario"))
                .andExpect(status().isOk());
    }

    @Test
    public void testObtenerProvedor() throws Exception {
        // 1. Creamos la entidad real que va a escupir el servicio mockeado
        InventarioDTO p1 = new InventarioDTO();
        p1.setIdInventario(1L);
        p1.setIdProducto(1L);
        p1.setCantidadDisponible(1);
        p1.setUbicacionBodega("Bodega 1");

        // 2. Creamos el DTO representativo
        InventarioDTO dto = new InventarioDTO();
        dto.setIdInventario(1L);
        dto.setIdProducto(1L);
        dto.setCantidadDisponible(1);
        dto.setUbicacionBodega("Bodega 1");

        // 3. Mockeamos el servicio: devuelve la entidad
        when(inventarioService.getInventarioById(1L)).thenReturn(p1);

        // 4. ¡EL PASO CLAVE! Como el controlador usa provedoreesAssembler.toModel(...),
        // obligamos al assembler mockeado a devolver un EntityModel para que no sea
        // null
        when(inventarioAssembler.toModel(any())).thenReturn(org.springframework.hateoas.EntityModel.of(dto));

        // El .andDo(print()) nos va a chismear el JSON exacto en los logs de la consola
        // mockMvc.perform(get("/proveedores/1"))
        // .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
        // .andExpect(status().isOk());

        // Verificamos las propiedades reales que la consola nos demostró que existen en
        // la raíz
        mockMvc.perform(get("/inventario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadDisponible").value(1)) // Muerde directo en la raíz
                .andExpect(jsonPath("$.ubicacionBodega").value("Bodega 1")); // Verifica HATEOAS
    }

    @Test
    public void testCrearProvedor() throws Exception {
        // 1. Creamos la entidad real que va a simular devolver el servicio
        InventarioDTO proveedorGuardado = new InventarioDTO();
        proveedorGuardado.setIdInventario(1L);
        proveedorGuardado.setIdProducto(1L);
        proveedorGuardado.setCantidadDisponible(1);
        proveedorGuardado.setUbicacionBodega("Bodega 1");

        // 2. Creamos el DTO que va a enviar el cliente en el JSON
        InventarioDTO proveedorReqDto = new InventarioDTO();
        proveedorReqDto.setIdInventario(1L);
        proveedorReqDto.setIdProducto(1L);
        proveedorReqDto.setCantidadDisponible(1);
        proveedorReqDto.setUbicacionBodega("Bodega 1");

        // 3. Configuras el Mock: Cuando el controlador llame al servicio pasándole
        // CUALQUIER entidad, devuelve la guardada
        when(inventarioService.guardarInventario(any(InventarioDTO.class))).thenReturn(proveedorGuardado);

        // 4. Ejecutas la petición simulada a la URL correcta (/provedores) y esperas un
        // OK (200)
        mockMvc.perform(post("/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proveedorReqDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testActualizarProvedor() throws Exception {
        // 1. Creamos la entidad editada que va a devolver el servicio
        InventarioDTO proveedorActualizado = new InventarioDTO();
        proveedorActualizado.setIdInventario(1L);
        proveedorActualizado.setIdProducto(1L);
        proveedorActualizado.setCantidadDisponible(1);
        proveedorActualizado.setUbicacionBodega("Bodega 1");

        // 2. Creamos el DTO con los cambios que manda el cliente
        InventarioDTO inventarioReqDto = new InventarioDTO();
        inventarioReqDto.setIdInventario(1L);
        inventarioReqDto.setIdProducto(1L);
        inventarioReqDto.setCantidadDisponible(1);
        inventarioReqDto.setUbicacionBodega("Bodega 1");

        // 3. Configuras el Mock para el método actualizar
        when(inventarioService.actualizarInventario(eq(1L), any(InventarioDTO.class))).thenReturn(proveedorActualizado);

        // 4. Ejecutas el PUT a la URL correcta
        mockMvc.perform(put("/inventario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inventarioReqDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testEliminarProvedor() throws Exception {
        doNothing().when(inventarioService).eliminarInventario(1L);
        mockMvc.perform(delete("/inventario/1"))
                .andExpect(status().isOk());
    }
}
