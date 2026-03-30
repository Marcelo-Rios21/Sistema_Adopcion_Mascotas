package com.veterinaria.veterinaria.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.veterinaria.veterinaria.model.Mascota;
import com.veterinaria.veterinaria.service.MascotaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping("/catalogo")
    public String verCatalogo(Model model) {
        model.addAttribute("mascotas", mascotaService.listarTodas());
        return "mascotas/catalogo";
    }

    @GetMapping("/catalogo/buscar")
    public String buscarMascotas(
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) String ubicacion,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) Integer edad,
            Model model) {

        List<Mascota> mascotas;

        if (especie != null && !especie.isBlank()) {
            mascotas = mascotaService.buscarPorEspecie(especie);
        } else if (ubicacion != null && !ubicacion.isBlank()) {
            mascotas = mascotaService.buscarPorUbicacion(ubicacion);
        } else if (genero != null && !genero.isBlank()) {
            mascotas = mascotaService.buscarPorGenero(genero);
        } else if (edad != null) {
            mascotas = mascotaService.buscarPorEdad(edad);
        } else {
            mascotas = mascotaService.listarTodas();
        }

        model.addAttribute("mascotas", mascotas);
        return "mascotas/catalogo";
    }

    @GetMapping("/admin/mascotas/nueva")
    public String mostrarFormularioNuevaMascota(Model model) {
        model.addAttribute("mascota", new Mascota());
        return "mascotas/form";
    }

    @GetMapping("/admin/mascotas")
    public String verAdministracionMascotas(Model model) {
        model.addAttribute("mascotas", mascotaService.listarTodas());
        return "mascotas/admin-lista";
    }

    @GetMapping("/admin/mascotas/editar/{id}")
    public String mostrarFormularioEditarMascota(@PathVariable Long id, Model model) {
        model.addAttribute("mascota", mascotaService.obtenerPorId(id));
        return "mascotas/form";
    }

    @PostMapping("/admin/mascotas/guardar")
    public String guardarMascota(@Valid @ModelAttribute("mascota") Mascota mascota,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "mascotas/form";
        }

        mascotaService.guardar(mascota);
        return "redirect:/catalogo";
    }
}
