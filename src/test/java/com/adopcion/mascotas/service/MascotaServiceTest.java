package com.adopcion.mascotas.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adopcion.mascotas.model.Mascota;
import com.adopcion.mascotas.repository.MascotaRepository;

@ExtendWith(MockitoExtension.class)
class MascotaServiceTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @InjectMocks
    private MascotaService mascotaService;

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
    void testGuardarMascota() {
        when(mascotaRepository.save(mascota1)).thenReturn(mascota1);

        Mascota resultado = mascotaService.guardar(mascota1);

        assertNotNull(resultado);
        assertEquals("Firulais", resultado.getNombre());
        verify(mascotaRepository, times(1)).save(mascota1);
    }

    @Test
    void testListarTodas() {
        when(mascotaRepository.findAll()).thenReturn(Arrays.asList(mascota1, mascota2));

        List resultado = mascotaService.listarTodas();

        assertEquals(2, resultado.size());
        assertEquals("Firulais", ((Mascota) resultado.get(0)).getNombre());
        assertEquals("Michi", ((Mascota) resultado.get(1)).getNombre());
        verify(mascotaRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorEspecie() {
        when(mascotaRepository.findByEspecieContainingIgnoreCase("Perro"))
                .thenReturn(List.of(mascota1));

        List resultado = mascotaService.buscarPorEspecie("Perro");

        assertEquals(1, resultado.size());
        assertEquals("Perro", ((Mascota) resultado.get(0)).getEspecie());
        verify(mascotaRepository, times(1)).findByEspecieContainingIgnoreCase("Perro");
    }

    @Test
    void testBuscarPorUbicacion() {
        when(mascotaRepository.findByUbicacionContainingIgnoreCase("Santiago"))
                .thenReturn(List.of(mascota1));

        List resultado = mascotaService.buscarPorUbicacion("Santiago");

        assertEquals(1, resultado.size());
        assertEquals("Santiago", ((Mascota) resultado.get(0)).getUbicacion());
        verify(mascotaRepository, times(1)).findByUbicacionContainingIgnoreCase("Santiago");
    }

    @Test
    void testBuscarPorGenero() {
        when(mascotaRepository.findByGeneroContainingIgnoreCase("Hembra"))
                .thenReturn(List.of(mascota2));

        List resultado = mascotaService.buscarPorGenero("Hembra");

        assertEquals(1, resultado.size());
        assertEquals("Hembra", ((Mascota) resultado.get(0)).getGenero());
        verify(mascotaRepository, times(1)).findByGeneroContainingIgnoreCase("Hembra");
    }

    @Test
    void testBuscarPorEdad() {
        when(mascotaRepository.findByEdad(5)).thenReturn(List.of(mascota1));

        List resultado = mascotaService.buscarPorEdad(5);

        assertEquals(1, resultado.size());
        assertEquals(5, ((Mascota) resultado.get(0)).getEdad());
        verify(mascotaRepository, times(1)).findByEdad(5);
    }

    @Test
    void testObtenerPorIdExistente() {
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota1));

        Mascota resultado = mascotaService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Firulais", resultado.getNombre());
        verify(mascotaRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerPorIdNoExistente() {
        when(mascotaRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> mascotaService.obtenerPorId(99L)
        );

        assertEquals("Mascota no encontrada con id: 99", exception.getMessage());
        verify(mascotaRepository, times(1)).findById(99L);
    }

    @Test
    void testEliminarPorIdExistente() {
        when(mascotaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(mascotaRepository).deleteById(1L);

        mascotaService.eliminarPorId(1L);

        verify(mascotaRepository, times(1)).existsById(1L);
        verify(mascotaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarPorIdNoExistente() {
        when(mascotaRepository.existsById(99L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> mascotaService.eliminarPorId(99L)
        );

        assertEquals("Mascota no encontrada con id: 99", exception.getMessage());
        verify(mascotaRepository, times(1)).existsById(99L);
        verify(mascotaRepository, never()).deleteById(99L);
    }
}