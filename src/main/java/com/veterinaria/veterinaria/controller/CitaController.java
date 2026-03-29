package com.veterinaria.veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.veterinaria.veterinaria.model.Cita;
import com.veterinaria.veterinaria.service.CitaService;

import jakarta.validation.Valid;

@Controller
public class CitaController {
    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping("/citas")
    public String listarCitas(Model model) {
        model.addAttribute("citas", citaService.listarTodas());
        return "citas/lista";
    }

    @GetMapping("/citas/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cita", new Cita());
        return "citas/form";
    }

    @PostMapping("/citas/guardar")
    public String guardarCita(@Valid @ModelAttribute("cita") Cita cita,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "citas/form";
        }

        citaService.guardar(cita);
        redirectAttributes.addFlashAttribute("mensajeExito", "Cita registrada correctamente.");
        return "redirect:/citas";
    }
}
