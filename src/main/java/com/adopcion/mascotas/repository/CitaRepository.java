package com.adopcion.mascotas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adopcion.mascotas.model.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long> {
}
