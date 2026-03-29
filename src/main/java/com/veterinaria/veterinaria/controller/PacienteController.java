package com.veterinaria.veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.veterinaria.veterinaria.model.Paciente;
import com.veterinaria.veterinaria.service.PacienteService;

import jakarta.validation.Valid;

@Controller
public class PacienteController {
    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/pacientes")
    public String listarPacientes(Model model) {
        model.addAttribute("pacientes", pacienteService.listarTodos());
        return "pacientes/lista";
    }

    @GetMapping("/pacientes/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes/form";
    }

    @PostMapping("/pacientes/guardar")
    public String guardarPaciente(@Valid @ModelAttribute("paciente") Paciente paciente,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "pacientes/form";
        }

        pacienteService.guardar(paciente);
        redirectAttributes.addFlashAttribute("mensajeExito", "Paciente registrado correctamente.");
        return "redirect:/pacientes";
    }
}
