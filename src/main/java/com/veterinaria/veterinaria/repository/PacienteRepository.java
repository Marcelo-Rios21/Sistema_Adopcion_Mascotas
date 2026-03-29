package com.veterinaria.veterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veterinaria.veterinaria.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
