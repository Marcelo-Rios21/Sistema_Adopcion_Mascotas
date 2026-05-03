package com.adopcion.mascotas.controller;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.adopcion.mascotas.model.Mascota;
import com.adopcion.mascotas.service.MascotaService;

@ExtendWith(MockitoExtension.class)
class MascotaApiControllerTest {

    @Mock
    private MascotaService mascotaService;

    @InjectMocks
    private MascotaApiController mascotaApiController;

    private Mascota mascota1;
    private Mascota mascota2;

    @BeforeEach
    void setUp() {
        mascota1 = new Mascota(
                1L,
                "Firulais",
                "Perro",
                "Labrador",
                5,
                "Macho",
                "Santiago",
                "Disponible",
                "firulais.jpg"
        );

        mascota2 = new Mascota(
                2L,
                "Michi",
                "Gato",
                "Siames",
                3,
                "Hembra",
                "Puente Alto",
                "Disponible",
                "michi.jpg"
        );
    }

    @Test
    void testListarMascotas() {
        List<Mascota> mascotas = Arrays.asList(mascota1, mascota2);

        when(mascotaService.listarTodas()).thenReturn(mascotas);

        List resultado = mascotaApiController.listarMascotas();

        assertEquals(2, resultado.size());
        assertEquals("Firulais", ((Mascota) resultado.get(0)).getNombre());
        assertEquals("Michi", ((Mascota) resultado.get(1)).getNombre());

        verify(mascotaService, times(1)).listarTodas();
    }

    @Test
    void testBuscarMascotasPorEspecie() {
        when(mascotaService.buscarPorEspecie("Perro")).thenReturn(List.of(mascota1));

        List resultado = mascotaApiController.buscarMascotas("Perro", null, null, null);

        assertEquals(1, resultado.size());
        assertEquals("Perro", ((Mascota) resultado.get(0)).getEspecie());

        verify(mascotaService, times(1)).buscarPorEspecie("Perro");
        verify(mascotaService, never()).listarTodas();
    }

    @Test
    void testBuscarMascotasPorUbicacion() {
        when(mascotaService.buscarPorUbicacion("Santiago")).thenReturn(List.of(mascota1));

        List resultado = mascotaApiController.buscarMascotas(null, "Santiago", null, null);

        assertEquals(1, resultado.size());
        assertEquals("Santiago", ((Mascota) resultado.get(0)).getUbicacion());

        verify(mascotaService, times(1)).buscarPorUbicacion("Santiago");
    }

    @Test
    void testBuscarMascotasPorGenero() {
        when(mascotaService.buscarPorGenero("Hembra")).thenReturn(List.of(mascota2));

        List resultado = mascotaApiController.buscarMascotas(null, null, "Hembra", null);

        assertEquals(1, resultado.size());
        assertEquals("Hembra", ((Mascota) resultado.get(0)).getGenero());

        verify(mascotaService, times(1)).buscarPorGenero("Hembra");
    }

    @Test
    void testBuscarMascotasPorEdad() {
        when(mascotaService.buscarPorEdad(5)).thenReturn(List.of(mascota1));

        List resultado = mascotaApiController.buscarMascotas(null, null, null, 5);

        assertEquals(1, resultado.size());
        assertEquals(5, ((Mascota) resultado.get(0)).getEdad());

        verify(mascotaService, times(1)).buscarPorEdad(5);
    }

    @Test
    void testBuscarMascotasSinFiltros() {
        when(mascotaService.listarTodas()).thenReturn(Arrays.asList(mascota1, mascota2));

        List resultado = mascotaApiController.buscarMascotas(null, null, null, null);

        assertEquals(2, resultado.size());

        verify(mascotaService, times(1)).listarTodas();
    }

    @Test
    void testCrearMascota() {
        when(mascotaService.guardar(mascota1)).thenReturn(mascota1);

        ResponseEntity respuesta = mascotaApiController.crearMascota(mascota1);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Firulais", ((Mascota) respuesta.getBody()).getNombre());

        verify(mascotaService, times(1)).guardar(mascota1);
    }

    @Test
    void testActualizarMascota() {
        Mascota datosActualizados = new Mascota(
                "Firulais Actualizado",
                "Perro",
                "Labrador",
                6,
                "Macho",
                "Santiago Centro",
                "Adoptado",
                "firulais_actualizado.jpg"
        );

        when(mascotaService.obtenerPorId(1L)).thenReturn(mascota1);
        when(mascotaService.guardar(mascota1)).thenReturn(mascota1);

        ResponseEntity respuesta = mascotaApiController.actualizarMascota(1L, datosActualizados);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());

        Mascota mascotaActualizada = (Mascota) respuesta.getBody();
        assertNotNull(mascotaActualizada);
        assertEquals("Firulais Actualizado", mascotaActualizada.getNombre());
        assertEquals(6, mascotaActualizada.getEdad());
        assertEquals("Adoptado", mascotaActualizada.getEstadoAdopcion());

        verify(mascotaService, times(1)).obtenerPorId(1L);
        verify(mascotaService, times(1)).guardar(mascota1);
    }

    @Test
    void testEliminarMascota() {
        ResponseEntity respuesta = mascotaApiController.eliminarMascota(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        assertNull(respuesta.getBody());

        verify(mascotaService, times(1)).eliminarPorId(1L);
    }
}