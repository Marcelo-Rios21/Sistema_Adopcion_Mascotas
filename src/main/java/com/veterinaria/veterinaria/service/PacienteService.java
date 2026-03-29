package com.veterinaria.veterinaria.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.veterinaria.veterinaria.model.Paciente;

@Service
public class PacienteService {
    private final List<Paciente> pacientes = new ArrayList<>();
    private Long siguienteId = 1L;

    public void guardar(Paciente paciente) {
        paciente.setId(siguienteId++);
        pacientes.add(paciente);
    }

    public List<Paciente> listarTodos() {
        return pacientes;
    }
}
