package com.veterinaria.veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/admin/mascotas/nueva")
    public String mostrarFormularioNuevaMascota(Model model) {
        model.addAttribute("mascota", new Mascota());
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

