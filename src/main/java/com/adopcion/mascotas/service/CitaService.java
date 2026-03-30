package com.adopcion.mascotas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adopcion.mascotas.model.Cita;

@Service
public class CitaService {
    private final List<Cita> citas = new ArrayList<>();
    private Long siguienteId = 1L;

    public void guardar(Cita cita) {
        cita.setId(siguienteId++);
        citas.add(cita);
    }

    public List<Cita> listarTodas() {
        return citas;
    }
}
