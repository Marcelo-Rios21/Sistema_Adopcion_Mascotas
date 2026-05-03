package com.adopcion.mascotas.controller;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.adopcion.mascotas.model.Mascota;
import com.adopcion.mascotas.service.MascotaService;

@ExtendWith(MockitoExtension.class)
class MascotaControllerTest {

    @Mock
    private MascotaService mascotaService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private MascotaController mascotaController;

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
    void testVerCatalogo() {
        List<Mascota> mascotas = Arrays.asList(mascota1, mascota2);
        when(mascotaService.listarTodas()).thenReturn(mascotas);

        String vista = mascotaController.verCatalogo(model);

        assertEquals("mascotas/catalogo", vista);
        verify(model, times(1)).addAttribute("mascotas", mascotas);
        verify(mascotaService, times(1)).listarTodas();
    }

    @Test
    void testBuscarMascotasPorEspecie() {
        List<Mascota> mascotas = List.of(mascota1);
        when(mascotaService.buscarPorEspecie("Perro")).thenReturn(mascotas);

        String vista = mascotaController.buscarMascotas("Perro", null, null, null, model);

        assertEquals("mascotas/catalogo", vista);
        verify(model, times(1)).addAttribute("mascotas", mascotas);
        verify(mascotaService, times(1)).buscarPorEspecie("Perro");
    }

    @Test
    void testBuscarMascotasPorUbicacion() {
        List<Mascota> mascotas = List.of(mascota1);
        when(mascotaService.buscarPorUbicacion("Santiago")).thenReturn(mascotas);

        String vista = mascotaController.buscarMascotas(null, "Santiago", null, null, model);

        assertEquals("mascotas/catalogo", vista);
        verify(model, times(1)).addAttribute("mascotas", mascotas);
        verify(mascotaService, times(1)).buscarPorUbicacion("Santiago");
    }

    @Test
    void testBuscarMascotasPorGenero() {
        List<Mascota> mascotas = List.of(mascota2);
        when(mascotaService.buscarPorGenero("Hembra")).thenReturn(mascotas);

        String vista = mascotaController.buscarMascotas(null, null, "Hembra", null, model);

        assertEquals("mascotas/catalogo", vista);
        verify(model, times(1)).addAttribute("mascotas", mascotas);
        verify(mascotaService, times(1)).buscarPorGenero("Hembra");
    }

    @Test
    void testBuscarMascotasPorEdad() {
        List<Mascota> mascotas = List.of(mascota1);
        when(mascotaService.buscarPorEdad(5)).thenReturn(mascotas);

        String vista = mascotaController.buscarMascotas(null, null, null, 5, model);

        assertEquals("mascotas/catalogo", vista);
        verify(model, times(1)).addAttribute("mascotas", mascotas);
        verify(mascotaService, times(1)).buscarPorEdad(5);
    }

    @Test
    void testBuscarMascotasSinFiltros() {
        List<Mascota> mascotas = Arrays.asList(mascota1, mascota2);
        when(mascotaService.listarTodas()).thenReturn(mascotas);

        String vista = mascotaController.buscarMascotas(null, null, null, null, model);

        assertEquals("mascotas/catalogo", vista);
        verify(model, times(1)).addAttribute("mascotas", mascotas);
        verify(mascotaService, times(1)).listarTodas();
    }

    @Test
    void testMostrarFormularioNuevaMascota() {
        String vista = mascotaController.mostrarFormularioNuevaMascota(model);

        assertEquals("mascotas/form", vista);
        verify(model, times(1)).addAttribute(eq("mascota"), any(Mascota.class));
    }

    @Test
    void testVerAdministracionMascotas() {
        List<Mascota> mascotas = Arrays.asList(mascota1, mascota2);
        when(mascotaService.listarTodas()).thenReturn(mascotas);

        String vista = mascotaController.verAdministracionMascotas(model);

        assertEquals("mascotas/admin-lista", vista);
        verify(model, times(1)).addAttribute("mascotas", mascotas);
        verify(mascotaService, times(1)).listarTodas();
    }

    @Test
    void testMostrarFormularioEditarMascota() {
        when(mascotaService.obtenerPorId(1L)).thenReturn(mascota1);

        String vista = mascotaController.mostrarFormularioEditarMascota(1L, model);

        assertEquals("mascotas/form", vista);
        verify(model, times(1)).addAttribute("mascota", mascota1);
        verify(mascotaService, times(1)).obtenerPorId(1L);
    }

    @Test
    void testEliminarMascota() {
        String vista = mascotaController.eliminarMascota(1L);

        assertEquals("redirect:/admin/mascotas", vista);
        verify(mascotaService, times(1)).eliminarPorId(1L);
    }

    @Test
    void testGuardarMascotaSinErrores() {
        when(bindingResult.hasErrors()).thenReturn(false);

        String vista = mascotaController.guardarMascota(mascota1, bindingResult);

        assertEquals("redirect:/catalogo", vista);
        verify(mascotaService, times(1)).guardar(mascota1);
    }

    @Test
    void testGuardarMascotaConErrores() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String vista = mascotaController.guardarMascota(mascota1, bindingResult);

        assertEquals("mascotas/form", vista);
        verify(mascotaService, never()).guardar(any(Mascota.class));
    }
}
